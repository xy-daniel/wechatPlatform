package com.crudsavior.wechatPlatform.menu;

/**
 * MsgType
 *
 * @author arctic
 * @date 2020/12/11
 **/
public enum RespType {

    //文本消息
    TEXT(1, "text"),
    //图片消息
    IMAGE(2, "image"),
    //语音消息
    VOICE(3, "voice"),
    //视频消息
    VIDEO(4, "video"),
    //小视频消息
    SHORT_VIDEO(5, "shortvideo"),
    //地理位置消息
    LOCATION(6, "location"),
    //链接消息
    LINK(7, "link");

    //枚举值
    private int code;
    //枚举描述
    private String msg;

    // ----------getter、setter----------

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    RespType() {
    }

    /**
     * 全参构造方法
     * @param code 枚举值
     * @param msg 枚举描述
     */
    RespType(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }
}
