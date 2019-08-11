package com.tw.util;

import java.io.*;

import com.tw.controller.AttachController;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
public class FtpUtil {
    private static Logger log = Logger.getLogger(AttachController.class);

    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        log.info("=============FtpUtil:uploadFile==开始上传文件到服务器=======\n");
        log.info("=============FtpUtil:uploadFile==basePath======="+basePath+"\n");
        log.info("=============FtpUtil:uploadFile==filePath======="+filePath+"\n");
        log.info("=============FtpUtil:uploadFile==filename======="+filename+"\n");
        boolean result = false;
        FTPClient ftp = new FTPClient();
        //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
        ftp.setControlEncoding("UTF-8");
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
        conf.setServerLanguageCode("zh");
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.info("==========FtpUtil:uploadFile==FTP服务器连接异常=========");
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {

                log.info("==========FtpUtil:uploadFile==FTP服务器中目录："+basePath+filePath+"不存在");
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            log.info("==========FtpUtil:uploadFile==FTP服务器中创建目录："+basePath+filePath+"失败");
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                log.info("==========FtpUtil:uploadFile==FTP服务器,保存文件时报错");
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        log.info("=============FtpUtil:downloadFile==开始下载文件=======\n");
        log.info("=============FtpUtil:downloadFile==remotePath======="+remotePath+"\n");
        log.info("=============FtpUtil:downloadFile==filename======="+fileName+"\n");
        log.info("=============FtpUtil:downloadFile==localPath======="+localPath+"\n");
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.info("==========FtpUtil:downloadFile==FTP服务器连接异常=========\n");
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            if (fs.length==0) { //如果文件夹中不存在任何文件，则直接返回
                log.info("==========FtpUtil:downloadFile==文件夹中不存在任何文件，则直接返回=========\n");
                ftp.disconnect();
                return result;
            }
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    if (!localFile.exists()) {
                        // 先得到文件的上级目录，并创建上级目录，在创建文件
                        localFile.getParentFile().mkdir();
                        localFile.createNewFile();
                    }
                    log.info("==========FtpUtil:downloadFile==远程文件下载到本地========="+ff.getName()+"\n");
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);

                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("==========FtpUtil:downloadFile==远程文件下载,FileNotFoundException========="+e.getMessage()+"\n");
        }
        catch (IOException e) {
            e.printStackTrace();
            log.info("==========FtpUtil:downloadFile==远程文件下载到本地出现异常========="+e.getMessage()+"\n");

        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

}
