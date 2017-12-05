package com.bigdata.mylibrary.base.bean;

/**
 * user:kun
 * Date:03/07/2017 or 2:46 PM
 * email:hekun@gamil.com
 * Desc: 统一网络请求的返回结果
 * 所有的实体类都必须继承
 */

public class NetResponse {
    private String code;
    private String msg;
    private String v;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
