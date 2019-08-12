package com.tw.common;

import com.tw.config.FtpConfig;
import com.tw.entity.DevicePicture;
import com.tw.entity.common.ConstantParam;
import com.tw.service.DevicePictureService;
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
 * @param: 图片目录监听处理类
 * @return:
 */
@Component
public class ListenerPictureAdaptor extends FileAlterationListenerAdaptor {

    private static Logger log = Logger.getLogger(ListenerPictureAdaptor.class);

    @Autowired
    private FtpConfig ftpConfig;

    /**
     * 业务
     **/
    @Resource(name = "devicePictureService")
    private DevicePictureService devicePictureService;

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
        log.info("====ListenerPictureAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);

        String myFileName = file.getName();
        String eventId =myFileName.substring(0,myFileName.lastIndexOf("."));
        String[] str = eventId.split("_");
        String serial = str[0];
        String densityPictureName = myFileName;
        String densityPicturePath =file.getAbsolutePath().replace(ftpConfig.getBasePath(),"");;
        String fileType="1";
        System.out.println("====ListenerPictureAdaptor:onFileCreate【文件创建】监控处理开始。文件名为：" + file);
        System.out.println("serial:"+serial);
        System.out.println("file.getPath():"+file.getPath());
        System.out.println("file.getAbsolutePath():"+file.getAbsolutePath());
        System.out.println("file.getParent():"+(file.getParent()));

        //如果文件名中包含_heat,则说明是生成的热力图，fileType为2；否则就是原始灰度图fileType为1
        if(myFileName.contains("_heat")){
            fileType="2";
        }
        DevicePicture picture=new DevicePicture(serial,fileType ,densityPictureName,densityPicturePath);
        picture.setIsValid(ConstantParam.IS_VALID_YES);
        devicePictureService.AddPicture(picture);
        log.info("====ListenerPictureAdaptor:onFileCreate【文件创建】监控处理结束=====" );
        System.out.println("====ListenerPictureAdaptor:onFileCreate【文件创建】监控处理结束=====" );
    }




    /**
     * 目录修改
     **/
    @Override
    public void onDirectoryChange(File directory) {
        log.info("====ListenerPictureAdaptor【目录修改】======"+directory.getName() );
        System.out.println("目录修改");
    }

    /**
     * 目录删除
     **/
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("====ListenerPictureAdaptor【目录删除】======"+directory.getName() );
        System.out.println("目录删除");
    }

    /**
     * 文件修改（对于文件名的修改，会先触发文件新增方法，再触发文件删除方法）
     **/
    @Override
    public void onFileChange(File file) {
        log.info("====ListenerPictureAdaptor【文件修改】======"+file.getAbsolutePath() );
        System.out.println("文件修改");
    }

    /**
     * 文件删除
     **/
    @Override
    public void onFileDelete(File file) {
        log.info("====ListenerPictureAdaptor【删除文件】======"+file.getAbsolutePath() );
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