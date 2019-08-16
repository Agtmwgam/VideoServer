package com.tw.form;

import lombok.Data;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/16
 * @Param:
 * @return:
 */
@Data
public class LogListForm {

    private String serial;

    private String startTime;

    private String endTime;

    private Integer pageNo = 1;

    private Integer pageSize = 15;

}
