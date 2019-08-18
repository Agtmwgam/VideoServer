package com.tw.service;


import com.tw.convert.Vuser2UserDTOConvert;
import com.tw.dao.DeviceDao;
import com.tw.dao.RootInfoDao;
import com.tw.dao.VUserDao;
import com.tw.dto.UserDeviceDTO;
import com.tw.entity.Device;
import com.tw.entity.RootInfo;
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
    private DeviceService deviceService;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private RootInfoDao rootInfoDao;

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
    public List<UserDeviceDTO> fuzzyQueryUserAndDeviceList(VUser user, int pageNo, int pageSize) {
        List<UserDeviceDTO> userDeviceDTOList = new ArrayList<>();
        try {
//          模糊搜索用户和设备号（含分页功能）
            List<VUser> userList = fuzzyQueryUserByPage(user,pageNo, pageSize);
            for (VUser tempUser : userList) {
                UserDeviceDTO userDeviceDTO = Vuser2UserDTOConvert.convert(tempUser);
                List<Device> deviceList = vUserDao.getDeviceByUserId(tempUser.getUserID());
                userDeviceDTO.setDeviceList(deviceList);
                userDeviceDTOList.add(userDeviceDTO);
            }
        } catch (Exception e) {
            log.error("查询用户错误！");
            log.error(e.toString());
            System.out.println(e.toString());
        }
        return userDeviceDTOList;
    }

//  TODO   用户管理分页查询
    public List<VUser> fuzzyQueryUserByPage(VUser vuser, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo-1) * pageSize);
        param.put("pageSize", pageSize * pageNo);
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


    //根据查询条件查总数
    public int getTotleOfUserAndDevice(VUser user) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", user.getUserID());
        param.put("nickName", user.getNickName());
        param.put("password", user.getPassword());
        param.put("phoneNumber", user.getPhoneNumber());
        return vUserDao.getTotleOfUserAndDevice(param);
    }
}
