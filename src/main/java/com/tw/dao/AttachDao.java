package com.tw.dao;

import com.tw.entity.AttachBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
@Mapper
public interface AttachDao {
    public Integer uploadFile(ArrayList<AttachBean> afiles);

    public String qryGuid();

}
