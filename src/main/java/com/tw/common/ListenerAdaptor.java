package com.tw.common;

import com.tw.config.FtpConfig;
import com.tw.entity.DeviceVideo;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DeviceVideoService;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.File;
/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/10
 * @param: 文件监听处理类
 * @return:
 */
@Component
public class ListenerAdaptor extends FileAlterationListenerAdaptor {

    private static Logger log = Logger.getLogger(ListenerAdaptor.class);

    @Autowired
    private FtpConfig ftpConfig;

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
        System.out.println("创建新目录");
    }

    /**
     * 文件创建
     **/
    @Override
    public void onFileCreate(File file) {
        log.info("====ListenerAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);

        String myFileName = file.getName();
        String eventId =myFileName.substring(0,myFileName.lastIndexOf("."));
        String[] str = eventId.split("_");
        String serial = str[0];
        String warningTime =  str[1];
        String warningVideoName = myFileName;
        String warningVideoPath =ftpConfig.getVideoPath()+ "/"+serial+"/"+myFileName;

        System.out.println("====ListenerAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);
        System.out.println("eventId:"+eventId);
        System.out.println("serial:"+serial);
        System.out.println("warningTime:"+warningTime);
        System.out.println("warningVideoName:"+warningVideoName);
        System.out.println("file.getPath():"+file.getPath());
        System.out.println("file.getAbsolutePath():"+file.getAbsolutePath());
        System.out.println("file.getParent():"+(file.getParent()));


        DeviceVideo video=new DeviceVideo(serial, eventId, warningVideoName,warningVideoPath,warningTime);
        video.setIsValid(ConstantParam.IS_VALID_YES);
        deviceVideoService.AddVideo(video);
        log.info("====ListenerAdaptor:onFileCreate【文件创建】监控处理结束=====" );
        System.out.println("====ListenerAdaptor:onFileCreate【文件创建】监控处理结束=====" );
    }




    /**
     * 目录修改
     **/
    @Override
    public void onDirectoryChange(File directory) {
        log.info("====ListenerAdaptor【目录修改】======"+directory.getName() );
        System.out.println("目录修改");
    }

    /**
     * 目录删除
     **/
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("====ListenerAdaptor【目录删除】======"+directory.getName() );
        System.out.println("目录删除");
    }

    /**
     * 文件修改（对于文件名的修改，会先触发文件新增方法，再触发文件删除方法）
     **/
    @Override
    public void onFileChange(File file) {
        log.info("====ListenerAdaptor【文件修改】======"+file.getAbsolutePath() );
        System.out.println("文件修改");
    }

    /**
     * 文件删除
     **/
    @Override
    public void onFileDelete(File file) {
        log.info("====ListenerAdaptor【删除文件】======"+file.getAbsolutePath() );
        System.out.println("删除文件：" + file);
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