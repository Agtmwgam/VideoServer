package com.tw.controller;

import com.tw.entity.AttachBean;
import com.tw.entity.ResultBean;
import com.tw.config.FtpConfig;
import com.tw.service.AttachService;
import com.tw.util.FtpUtil;
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
import java.util.ArrayList;
import java.util.*;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */

@Controller
@RequestMapping(value = "/attach")
public class AttachController  extends BaseController {

    @Autowired
    private AttachService attachService;

    @Autowired
    private FtpConfig ftpConfig;

    @RequestMapping(value = "person", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String attach(@RequestBody AttachBean attach, HttpServletRequest request, HttpServletResponse response) {

        //attachService.update(attach);
        return renderString(response, attach);
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String upload(@RequestParam(name = "fileType", required = false) String fileType, HttpServletRequest request,
                         HttpServletResponse response, Model model) {

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {

            // 结果文件
            ArrayList<AttachBean> afiles = new ArrayList();

            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> iter = multiRequest.getMultiFileMap();

            for (Map.Entry<String, List<MultipartFile>> entry : iter.entrySet()) {
                List<MultipartFile> files = multiRequest.getFiles(entry.getKey());
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        String myFileName = file.getOriginalFilename();
                        // 记录上传开始时间
                        int pre = (int) System.currentTimeMillis();
                        // 开始传输到ftp
                        try {
                            boolean result = FtpUtil.uploadFile(ftpConfig.getHost(), ftpConfig.getPort(),
                                    ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath(),
                                    ftpConfig.getFilePath(), myFileName, file.getInputStream());
                            //result = true;
                            // boolean result = true;
                            if (result) {
                                AttachBean atb = new AttachBean();
                                atb.setaName(myFileName);
                                atb.setaType(fileType);
                                afiles.add(atb);
                            } else {
                                ResultBean rb = new ResultBean();
                                rb.setResult("false");
                                rb.setMsg("附件" + myFileName + "上传失败,请重试！");
                                return renderString(response, rb);
                            }

                            // boolean result = true;
                            int finaltime = (int) System.currentTimeMillis();
                            System.out.println("文件【" + myFileName + "】传输的结果是：" + result + ",文件大小是：" + file.getSize()
                                    + ",传输用时:" + (finaltime - pre));

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }

            }

            // debug
            for (AttachBean ab : afiles) {
                System.out.println("ab.before=" + ab.toString());
            }

            // 入库处理
            String nm = null;
            if (afiles != null && afiles.size() > 0)
                nm = attachService.uploadFile(afiles);

            // debug
            for (AttachBean ab : afiles) {
                System.out.println("ab=" + ab.toString());
            }

            if (nm != null) {
                ResultBean rb = new ResultBean();
                rb.setResult("true");
                rb.setMsg("上传成功");
                rb.setId(nm);
                return renderString(response, rb);
            } else {
                ResultBean rb = new ResultBean();
                rb.setResult("false");
                rb.setMsg("上传失败");
                return renderString(response, rb);
            }

        }
        ResultBean rb = new ResultBean();
        rb.setResult("false");
        rb.setMsg("未检测到附件.请上传对应格式附件.");

        return renderString(response, rb);
    }

    @GetMapping("/download")
    public String download(@RequestParam(name = "fileId", required = false) String fileId) {
        String fileName = "welcome.txt";
        String fileLocalPath = "";
        String fileRemotePath = "";
        /*boolean result = FtpUtil.downloadFile(ftpConfig.getHost(), ftpConfig.getPort(),
                ftpConfig.getUsername(), ftpConfig.getPassword(), ftpConfig.getBasePath(),
                ftpConfig.getFilePath(),  );
        if (result) {
            log.info("=======下载文件"+ fileName +"成功=======");
        } else {
            log.info("=======下载文件"+ fileName +"失败=======");
        }*/
        return null;
    }


}