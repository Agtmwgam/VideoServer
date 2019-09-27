package com.tw.common;

import com.tw.convert.String2DateConvert;
import com.tw.entity.DeviceVideo;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DeviceVideoService;
import com.tw.util.VideoFormatConvertUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @Author: lushiqin
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

    VideoFormatConvertUtil fileUtil=new VideoFormatConvertUtil();

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
     **/
    @Override
    public void onFileCreate(File file) {
        log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);

        String myFileName = file.getName();
        String eventId =myFileName.substring(0,myFileName.lastIndexOf("."));
        String[] str = eventId.split("_");
        String serial = str[0];
        String warningTime =  str[1];
        if(warningTime!=null){
            warningTime=FMT.format(String2DateConvert.convert(warningTime));
        }

        String warningVideoName = myFileName;
        String warningVideoPath =file.getAbsolutePath().replace("/home/ftp123","");;

        log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);
        log.info("eventId:"+eventId);
        log.info("serial:"+serial);
        log.info("warningTime:"+warningTime);
        log.info("warningVideoName:"+warningVideoName);
        log.info("file.getPath():"+file.getPath());
        log.info("file.getAbsolutePath():"+file.getAbsolutePath());
        log.info("file.getParent():"+(file.getParent()));


        DeviceVideo video=new DeviceVideo(serial, eventId, warningVideoName,warningVideoPath,warningTime);
        video.setIsValid(ConstantParam.IS_VALID_YES);
        deviceVideoService.AddVideo(video);
        log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理结束=====" );
        log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】监控处理结束=====" );

        String inputfile=file.getAbsolutePath();
        try {
            fileUtil.frameRecord(inputfile,inputfile);
        }catch (Exception e){
            log.info("====ListenerVideoAdaptor:onFileCreate【文件创建】文件转换出错====" );
        }


    }




    /**
     * 目录修改
     **/
    @Override
    public void onDirectoryChange(File directory) {
        log.info("====ListenerVideoAdaptor【目录修改】======"+directory.getName() );
        log.info("目录修改");
    }

    /**
     * 目录删除
     **/
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("====ListenerVideoAdaptor【目录删除】======"+directory.getName() );
        log.info("目录删除");
    }

    /**
     * 文件修改（对于文件名的修改，会先触发文件新增方法，再触发文件删除方法）
     **/
    @Override
    public void onFileChange(File file) {
        log.info("====ListenerVideoAdaptor【文件修改】======"+file.getAbsolutePath() );
        log.info("文件修改");
    }

    /**
     * 文件删除
     **/
    @Override
    public void onFileDelete(File file) {
        log.info("====ListenerVideoAdaptor【删除文件】======"+file.getAbsolutePath() );
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