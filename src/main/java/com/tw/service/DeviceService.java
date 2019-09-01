package com.tw.service;

import com.tw.convert.Device2DeviceAndUserNameDTOConvert;
import com.tw.dao.DeviceDao;
import com.tw.dao.UserDeviceRelateDao;
import com.tw.dto.DeviceAndUserNameDTO;
import com.tw.entity.Device;
import com.tw.entity.UserDeviceRelate;
import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDeviceRelateDao userDeviceRelateDao;

    private static Logger log = Logger.getLogger(VUserService.class);

    public int addDevice(Device device) {
        return deviceDao.addDevice(device);
    }

    public Integer deleteDevice(int deviceId) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("isValid", ConstantParam.IS_VALID_NO);
        return deviceDao.deleteDevice(param);
    }


    public Integer updateDevice(Device device) {
        return deviceDao.updateDevice(device);
    }

    //根据分组id获得设备列表
    public List<Device> getDeviceByGroupId(int groupId) {
        return deviceDao.getDeviceByGroupId(groupId);
    }

    public Device getDeviceById(int deviceId) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("isValid", ConstantParam.IS_VALID_YES);
        return deviceDao.getDeviceById(param);
    }


    public List<Device> getDeviceByCoditionPage(Device device, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo - 1) * pageSize);
        param.put("pageSize", pageSize * pageNo);
        param.put("deviceId", device.getDeviceId());
        param.put("deviceName", device.getDeviceName());
        param.put("serial", device.getSerial());
        param.put("deviceVerifyCode", device.getDeviceVerifyCode());
        param.put("deviceType", device.getDeviceType());
        param.put("softVersion", device.getSoftVersion());
        param.put("productDate", device.getProductDate());
        param.put("deviceStatus", device.getDeviceStatus());
        param.put("isOnline", device.getIsOnline());
        param.put("ipAddress", device.getIpAddress());
        param.put("isValid", device.getIsValid());
        return deviceDao.getDeviceByCoditionPage(param);
    }


    public List<Device> getDeviceByCodition(Device device) {
        return deviceDao.getDeviceByCodition(device);
    }

    //模糊查询
    public List<DeviceAndUserNameDTO> getDeviceLikeCondition(Device device, int pageNo, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (pageNo - 1) * pageSize);
        param.put("end", (pageSize * pageNo));
        param.put("serial", device.getSerial());
        param.put("deviceType", device.getDeviceType());
        param.put("productDate", device.getProductDate());
        param.put("isValid ", '1');

//        模糊查询   --by liutianwen
        //返回前端的信息体
        List<DeviceAndUserNameDTO> DeviceAndUserNameDTOList = new ArrayList<>();
        //创建临时对象(主要存放设备信息device和用户名称nickName)
        VUser user = new VUser();
        DeviceAndUserNameDTO deviceAndUserNameDTO = new DeviceAndUserNameDTO();
        try {
            // 模糊查询出deviceList
            List<Device> deviceList = deviceDao.getDeviceLikeCondition(param);
            // 把设备信息device和用户名称nickName对应存放到返回信息体
            for (Device tempdevice : deviceList) {
                // 根据deviceId找到唯一对应的nickName
                Integer deviceId=tempdevice.getDeviceId();
                user = userDeviceRelateDao.getUserByDeviceID(deviceId);
                if(user==null){
                    user=new VUser();
                    user.setNickName(ConstantParam.NO_USER);
                }
                deviceAndUserNameDTO = Device2DeviceAndUserNameDTOConvert.convert(tempdevice);
                deviceAndUserNameDTO.setNickName(user.getNickName());
                DeviceAndUserNameDTOList.add(deviceAndUserNameDTO);
            }
        } catch (Exception e) {
            log.error("查询用户错误！");
            log.error(e.toString());
            e.printStackTrace();
        }
        return DeviceAndUserNameDTOList;
    }


    /**
     * @param user
     * @return
     * @author liutianwen
     * @desc 根据传入vuser信息查看设备号
     */
    public List<String> getDeviceByUser(VUser user) {
        return deviceDao.getDeviceByUser(user);
    }


    /**
     * @param userDeviceRelate
     * @return
     * @author liutianwen
     * @desc 增加用户设备
     */
    public void addUserDevice(UserDeviceRelate userDeviceRelate) {
        try {
            userDeviceRelateDao.addUserDevice(userDeviceRelate);
        } catch (Exception e) {
            log.error("addUserDevice erro:" + e.toString());
            e.printStackTrace();
        }
    }


    //根据查询条件获得查询总数
    public int getTotalOfCondition(Device device) {
        return deviceDao.getTotalOfCondition(device);
    }

    //拿到数量
    public int getCountOfLikCondition(Device device) {
        return deviceDao.getCountOfLikCondition(device);
    }

    public int updateDeviceName(Device device) {
        return deviceDao.updateDeviceName(device);
    }

    //修改设备的在库状态（库存/出库）,接收参数为 deviceId和status值，如 12，'1'
    public int updateDeviceStatus(int deviceId, char statuesCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("deviceId", deviceId);
        param.put("status", statuesCode);
        return deviceDao.updateDeviceStatus(param);
    }

    public List<Device> getDeviceBySerial(String serial) {
        return deviceDao.getDeviceBySerial(serial);

    }
}