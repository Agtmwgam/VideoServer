package com.tw.service;


import com.tw.convert.VUserToVUserAndDeviceDTOConvert;
import com.tw.dao.DeviceDao;
import com.tw.dao.VUserDao;
import com.tw.dto.UserAndDeviceSerialDTO;
import com.tw.entity.Device;
import com.tw.entity.VUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<UserAndDeviceSerialDTO> fuzzyQueryUserAndDeviceList(VUser user, int pageNo, int pageSize) {
        List<VUser> userList = null;
        List<String> serialList = null;
        UserAndDeviceSerialDTO uad = new UserAndDeviceSerialDTO();
        List<UserAndDeviceSerialDTO> uAdList = new ArrayList<UserAndDeviceSerialDTO>();
        try {
//          模糊搜索用户和设备号（含分页功能）
            userList = fuzzyQueryUserByPage(user,pageNo, pageSize);
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

//  TODO   用户管理分页查询
    public List<VUser> fuzzyQueryUserByPage(VUser vuser, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo-1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("userId", vuser.getUserID());
        param.put("nickName", vuser.getNickName());
        param.put("password", vuser.getPassword());
        param.put("phoneNumber", vuser.getPhoneNumber());

        return vUserDao.fuzzyQueryUserByPage(param);
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
