package com.tw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
@Component
@Mapper
public interface AttachDao {

    public String qryGuid();

}
