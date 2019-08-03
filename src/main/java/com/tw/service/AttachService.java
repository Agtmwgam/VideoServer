package com.tw.service;

import com.tw.dao.AttachDao;
import com.tw.dao.UserDao;
import com.tw.entity.AttachBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
@Service
public class AttachService {
    @Autowired
    private AttachDao dao;

    public String uploadFile(ArrayList<AttachBean> afiles) {
        String sn = dao.qryGuid();
        for (AttachBean atb : afiles)
            atb.setaId(sn);
        int nm = dao.uploadFile(afiles);
        if (nm == afiles.size())
            return sn;
        else
            return null;
    }
}
