package com.tw.entity;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
public class ResultBean {
    private String result;//结果：true 成功；false 失败
    private String msg;//提示语
    private String id;//成功时返回的编号

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
