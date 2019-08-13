package com.tw.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import lombok.Data;

/**
 * @author liutianwen
 * @Description: 实体类基类
 * @date 2019年8月3日
 */
@Data
public class BaseEntity {
    //	创建时间
    private Date createTime;
    //	是否可用（1可用0不可用）
    private char isValid;
////    @Value("1")
//    private char usable;

    //	默认该类可用，创建时间为当前时间
//    public BaseEntity() {
//        String sIsValid = String.valueOf(isValid);
//        if (!StringUtils.isEmpty(sIsValid)) {
//            this.isValid = isValid;
//        } else {
//            this.isValid = usable;
//        }
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            this.createTime = sdf.parse(sdf.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }


}
