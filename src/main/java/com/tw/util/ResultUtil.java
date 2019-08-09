package com.tw.util;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/9
 * @Param:
 * @return:
 */
public class ResultUtil {


    /**
     * 带data信息的成功返回
     * @param object  data内容
     * @return
     */
    public static ResponseInfo success(Object object){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setCode("0000");
        responseInfo.setData(object);
        return responseInfo;
    }


    /**
     * 不带信息的成功返回
     * @return
     */
    public static ResponseInfo success(){
        return success(null);
    }


    /**
     * 带错误信息的失败返回
     * @param desc  错误信息
     * @return
     */
    public static ResponseInfo error(String desc){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setCode("9999");
        responseInfo.setMessage(desc);
        return responseInfo;
    }

    /**
     * 带page和payload的成功返回
     * @param object  payload内容
     * @param pageNo  显示第几页的数据
     * @param pageSize  每页显示多少条数据
     * @return
     */
    public static ResponseInfo successPage(Object object, Integer pageNo, Integer pageSize, Long total){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setCode("9999");
        responseInfo.setData(object);
        responseInfo.setPageNo(pageNo);
        responseInfo.setPageSize(pageSize);
        responseInfo.setTotal(total);
        return responseInfo;
    }



}
