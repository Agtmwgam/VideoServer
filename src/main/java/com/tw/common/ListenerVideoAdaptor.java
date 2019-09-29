package com.tw.common;

import com.tw.convert.String2DateConvert;
import com.tw.entity.DeviceVideo;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DeviceVideoService;
import com.tw.util.VideoFormatConvertUtil;
import lombok.Synchronized;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @Author: lushiqin & LIUTIANWEN
 * @Description:
 * @Date: 2019/8/10
 * @param: 视频目录监听处理类
 * @return:
 */
@Component
public class ListenerVideoAdaptor extends FileAlterationListenerAdaptor {

    private static Logger log = Logger.getLogger(ListenerVideoAdaptor.class);

    // 全局统一时间格式化格式
    SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    VideoFormatConvertUtil fileUtil = new VideoFormatConvertUtil();

    /**
     * 业务
     **/
    @Resource(name = "deviceVideoService")
    private DeviceVideoService deviceVideoService;

    /**
     * 目录创建
     **/
    @Override
    public void onDirectoryCreate(File directory) {
        log.info("创建新目录");
    }

    /**
     * 文件创建
     * tips:传入视频名同evenetID,示例规定格式为 T42683512_2018-1-29T0-10-1，
     * T42683512 为srial，2018-1-29为warningTime，warningVideoName 为 T42683512_2018-1-29T0-10-1.mp4
     * warningVideoPath 为文件的绝对路径去掉/home/ftp123
     * 如果本地测试需要把路径下中的/ 换成\\
     **/
    @Override
    public void onFileCreate(File file) {
        log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);

        //需要转化的文件路径
        String inputfile = file.getAbsolutePath();
        //如果是transform路径下的文件则不进行处理
        Boolean isDeal = inputfile.matches(ConstantParam.VERIFYPATH);
        if (!isDeal) {
            String myFileName = file.getName();
            String eventId = myFileName.substring(0, myFileName.lastIndexOf("."));
            String[] str = eventId.split("_");
            String serial = str[0];
            String warningTime = str[1];
            if (warningTime != null) {
                warningTime = FMT.format(String2DateConvert.convert(warningTime));
            }

            String warningVideoName = myFileName;
            String warningVideoPath = file.getAbsolutePath().replace(ConstantParam.FTP_PATH, "");

//           打印LOG日志
            log.info("eventId:" + eventId);
            log.info("serial:" + serial);
            log.info("warningTime:" + warningTime);
            log.info("warningVideoName:" + warningVideoName);
            log.info("file.getPath():" + file.getPath());
            log.info("file.getAbsolutePath():" + file.getAbsolutePath());
            log.info("file.getParent():" + (file.getParent()));


            DeviceVideo video = new DeviceVideo(serial, eventId, warningVideoName, warningVideoPath, warningTime);
            video.setIsValid(ConstantParam.IS_VALID_YES);


            //创建transform文件夹
            //本地测试用
//            String dirPath = inputfile.substring(0, inputfile.lastIndexOf("\\")) + "\\transform";
            // 服务器用
            String dirPath = inputfile.substring(0, inputfile.lastIndexOf("/")) + "/transform";
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            int transResault =-1;
            //视频转换输出路径
//            本地测试用
//            String outputfile = dirPath + inputfile.substring(inputfile.lastIndexOf("\\"), inputfile.length());
//            服务器用
            String outputfile = dirPath + inputfile.substring(inputfile.lastIndexOf("/"), inputfile.length());
            try {
                transResault = fileUtil.frameRecord(inputfile, outputfile);
            } catch (Exception e) {
                log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件转换出错====");
                log.info(e.toString());
            }

            //如果没有转换，则数据库引用原有视频地址
            if(transResault==0) {
                log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件没有转换，原文件:" + inputfile);
            }

            //如果视频转换过，则数据库中引用转换过后的地址
            if(transResault==1){
                String transformVideoPath = outputfile.replace(ConstantParam.FTP_PATH, "");
                video.setWarningVideoPath(transformVideoPath);
                log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件转换成功，原文件地址:" + inputfile);
                log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件转换成功，转换后地址:" + outputfile);
            }

            //  如果转换过程没有问题，则文件入库
            try {
                if(transResault!=-1){
                    deviceVideoService.AddVideo(video);
                    log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件入库成功=====");
                }
            }catch (Exception e){
                log.error("====ListenerVideoAdaptor:onFileCreate【文件创建】文件入库失败=====");
                log.error(e.getMessage());
            }

            log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理结束=====");

        }
    }


    /**
     * 目录修改
     **/
    @Override
    public void onDirectoryChange(File directory) {
        log.info("====ListenerVideoAdaptor【目录修改】======" + directory.getName());
        log.info("目录修改");
    }

    /**
     * 目录删除
     **/
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("====ListenerVideoAdaptor【目录删除】======" + directory.getName());
        log.info("目录删除");
    }

    /**
     * 文件修改（对于文件名的修改，会先触发文件新增方法，再触发文件删除方法）
     **/
    @Override
    public void onFileChange(File file) {
        log.info("====ListenerVideoAdaptor【文件修改】======" + file.getAbsolutePath());
        log.info("文件修改");
    }

    /**
     * 文件删除
     **/
    @Override
    public void onFileDelete(File file) {
        log.info("====ListenerVideoAdaptor【删除文件】======" + file.getAbsolutePath());
        log.info("删除文件：" + file);
    }

    /**
     * 扫描开始
     **/
    @Override
    public void onStart(FileAlterationObserver observer) {
    }

    /**
     * 扫描结束
     **/
    @Override
    public void onStop(FileAlterationObserver observer) {
    }
}