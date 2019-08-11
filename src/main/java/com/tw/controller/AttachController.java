package com.tw.controller;

import com.tw.util.JsonMapperUtil;
import com.tw.config.FtpConfig;
import com.tw.util.FtpUtil;
import com.tw.util.ResponseInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author: lushiqin
 * @Description:  固件管理（上传下载）
 * @Date: 2019/8/3
 * @return:
 */
@RestController
@Controller
@RequestMapping("/attach/")
public class AttachController  {

    private static Logger log = Logger.getLogger(AttachController.class);

    @Autowired
    private FtpConfig ftpConfig;


    /***
     * 上传固件至ftp服务器（单文件上传）
     * 前端一个表单，选择文件，form的enctype为multipart/form-data 报文头不需要设置Content-Type
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        log.info("=====/upload上传固件,从前端获取到的文件名=====file:"+file.getOriginalFilename());
        ResponseInfo  responseInfo = new ResponseInfo();

        if (file.isEmpty()) {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("未检测到附件.请上传对应格式附件.");
            return JsonMapperUtil.toJsonString(responseInfo);
        }

        //给文件一个唯一的名字，上传过程采用覆盖的形式。
        String myFileName = file.getOriginalFilename();
        String fileNewName="T_ML_UPGRADE.exe";
        // 记录上传开始时间
        int pre = (int) System.currentTimeMillis();

        try {// 开始传输到ftp
            boolean result = FtpUtil.uploadFile(ftpConfig.getHost(), ftpConfig.getPort(),
                    ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath(),
                    ftpConfig.getFirmwarePath(), fileNewName, file.getInputStream());
            if (result) {
                responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
                responseInfo.setMessage("附件" + myFileName + "上传成功");
            } else {
                responseInfo.setCode(ResponseInfo.CODE_ERROR);
                responseInfo.setMessage("附件" + myFileName + "上传失败,请重试！");
            }

        } catch (IOException e) {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("附件" + myFileName + "上传失败,服务器异常！");
        }finally {
            return JsonMapperUtil.toJsonString(responseInfo);
        }

    }



    /***
     * 从指定的ftp.firmwarePath目录下载固件，指定下载的路径
     * @return
     */
    @GetMapping("/downloadFirmware")
    public String downloadFirmware(@RequestParam(value = "fileLocalPath") String fileLocalPath) {
        log.info("=====/downloadFirmware下载固件,从前端获取到的路径=====fileLocalPath:"+fileLocalPath);
        //fileLocalPath="d:\\";
        //根据前端传递过来的fileId，在数据库中查询文件名称、文件远端路径
        String fileName = "T_ML_UPGRADE.exe";
        //根据文件类型判断传递过来的是图片还是视频，选择远程ftp服务器中的存放路径
        boolean result = FtpUtil.downloadFile(ftpConfig.getHost(), ftpConfig.getPort(),
                ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath()+ftpConfig.getFirmwarePath(),
                fileName,fileLocalPath );

        ResponseInfo responseInfo = new ResponseInfo<>();

        if (result) {
            responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
            responseInfo.setMessage("附件下载成功,请前往"+fileLocalPath+"查看文件。");

        } else {
            responseInfo.setCode(ResponseInfo.CODE_ERROR);
            responseInfo.setMessage("附件下载失败，请重试或者联系管理员");
        }
        return JsonMapperUtil.toJsonString(responseInfo);
    }


    /***
     * 暂时不用 （多文件上传）
     * @Description:
     * @return
     */
    @RequestMapping(value = "upload1", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseInfo<String>  upload( HttpServletRequest request,
                         HttpServletResponse response, Model model) {

        ResponseInfo  responseInfo = new ResponseInfo();

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {

            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> iter = multiRequest.getMultiFileMap();

            for (Map.Entry<String, List<MultipartFile>> entry : iter.entrySet()) {
                List<MultipartFile> files = multiRequest.getFiles(entry.getKey());
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        String myFileName = file.getOriginalFilename();
                        String fileType=myFileName.substring(myFileName.lastIndexOf(".")+1,myFileName.length());
                        //给文件一个唯一的名字，上传过程采用覆盖的形式。
                        String fileNewName="T_ML_V0.0.1.txt";
                        // 记录上传开始时间
                        int pre = (int) System.currentTimeMillis();
                        // 开始传输到ftp
                        try {
                            boolean result = FtpUtil.uploadFile(ftpConfig.getHost(), ftpConfig.getPort(),
                                    ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath(),
                                    ftpConfig.getFirmwarePath(), fileNewName, file.getInputStream());
                            if (result) {
                                responseInfo.setCode(ResponseInfo.CODE_SUCCESS);
                                responseInfo.setMessage("附件" + myFileName + "上传成功");
                            } else {
                                responseInfo.setCode(ResponseInfo.CODE_ERROR);
                                responseInfo.setMessage("附件" + myFileName + "上传失败,请重试！");
                                return responseInfo;
                            }

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }

            }

        }
        responseInfo.setCode(ResponseInfo.CODE_ERROR);
        responseInfo.setMessage("未检测到附件.请上传对应格式附件.");

        return responseInfo;
    }



}