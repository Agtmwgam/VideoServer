package com.tw.service;


import com.tw.convert.VUserToVUserAndDeviceDTOConvert;
import com.tw.dao.DeviceDao;
import com.tw.dao.VUserDao;
import com.tw.dto.UserAndDeviceSerialDTO;
import com.tw.entity.VUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Service
public class VUserService {

    @Autowired
    private VUserDao vUserDao;

    @Autowired
    private DeviceDao deviceDao;

    private static Logger log = Logger.getLogger(VUserService.class);

    // 新建一个用户
    @Transactional
    public void creatUser(VUser user) {
        try {
            vUserDao.creatUser(user);
        } catch (Exception e) {
            log.error("创建用户失败！");
            log.error(e.toString());
        }
    }

    //	查找用户
    @Transactional
    public VUser queryUser(VUser user) {
        return vUserDao.queryUser(user);
    }

    //模糊查找用户
    @Transactional
    public List<VUser> fuzzyQueryUser(VUser user) {
        List<VUser> userList = null;
        try {
            userList = vUserDao.fuzzyQueryUser(user);
        } catch (Exception e) {
            log.error("查询用户错误！");
            log.error(e.toString());
        }
        return userList;
    }

    //模糊查找用户详情(用户管理界面)
    @Transactional
    public List<UserAndDeviceSerialDTO> fuzzyQueryUserAndDeviceList(VUser user) {
        List<VUser> userList = null;
        List<String> serialList = null;
        UserAndDeviceSerialDTO uad = new UserAndDeviceSerialDTO();
        List<UserAndDeviceSerialDTO> uAdList = new ArrayList<UserAndDeviceSerialDTO>();
        try {
            userList = vUserDao.fuzzyQueryUser(user);
            for (VUser tempUser : userList) {
                serialList = deviceDao.getDeviceByUser(tempUser);
                uad = VUserToVUserAndDeviceDTOConvert.convert(tempUser, serialList);
                uAdList.add(uad);
            }
        } catch (Exception e) {
            log.error("查询用户错误！");
            log.error(e.toString());
            System.out.println(e.toString());
        }
        return uAdList;
    }


    //更新客户
    public Integer modifyUser(VUser user) {
        Integer num = 0;
        try {
            num = vUserDao.modifyUser(user);
        } catch (Exception e) {
            log.error("更新用户错误！");
            log.error(e.toString());
        }
        return num;
    }

    //逻辑删除用户
    public Integer delUser(VUser user) {
        Integer num = 0;
        try {
            num = vUserDao.delUser(user);
        } catch (Exception e) {
            log.error("更新用户错误！");
            log.error(e.toString());
        }
        return num;
    }


}
