package com.crudsavior.wechatPlatform.status;

/**
 * EventMsgType
 * 接收事件推送类型
 * @author arctic
 * @date 2020/12/11
 **/
public class EventMsgType {

    //订阅事件推送、用户未关注时，进行关注后的事件推送
    public static final String SUBSCRIBE = "subscribe";
    //取消订阅事件推送
    public static final String UNSUBSCRIBE = "unsubscribe";
    //用户已关注时的事件推送
    public static final String SCAN = "SCAN";
    //上报地理位置事件推送
    public static final String LOCATION = "LOCATION";
    //自定义菜单事件推送
    public static final String CLICK = "CLICK";
    //点击菜单跳转链接时的事件推送
    public static final String VIEW = "VIEW";

}
