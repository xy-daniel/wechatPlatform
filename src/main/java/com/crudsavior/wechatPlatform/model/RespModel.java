package com.crudsavior.wechatPlatform.model;

/**
 * RespModel
 *
 * @author arctic
 * @date 2020/12/16
 **/
public class RespModel {

    private Integer errcode;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public RespModel() {
    }

    public RespModel(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
}
