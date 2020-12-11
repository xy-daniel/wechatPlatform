package com.crudsavior.wechatPlatform.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.crudsavior.wechatPlatform.status.EventMsgType;
import com.crudsavior.wechatPlatform.status.RequestMsgType;
import com.crudsavior.wechatPlatform.status.ResponseMsgType;
import com.crudsavior.wechatPlatform.utils.HttpUtil;
import com.crudsavior.wechatPlatform.utils.MsgHandleUtil;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * IndexController
 *
 * @author arctic
 * @date 2020/12/2
 **/
@RestController
public class IndexController {

    @Value("${com.crudsavior.appID}")
    String appID;
    @Value("${com.crudsavior.appsecret}")
    String appsecret;
    @Value("${com.crudsavior.token}")
    String token;

    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    /**
     * 连接微信服务器
     * 校验方式:
     * 1、将参与微信加密签名的三个参数timestamp,nonce,token按照字典序排序组合在一起形成一个数组
     * 2、将数组里所有参数拼接成一个字符串进行sha1加密
     * 3、加密完成生成一个signature和微信发送过来的进行对比
     *     如果一样，说明消息来自于微信服务器，返回echostr给微信服务器
     *     如果不一样，说明不是微信服务器发送的消息，返回error
     * @param echostr 随机字符串
     * @return 随机字符串 或者 error
     */
    @GetMapping("/")
    public String index(String echostr) {
        log.info("接收到的参数：" + echostr);
        return echostr;
    }

    /**
     * 接收到的数据
     * xml:
     * <xml>
     *     <ToUserName><![CDATA[gh_da292587e6af]]></ToUserName>   开发者微信号
     *     <FromUserName><![CDATA[olj7y6D2pBjilT5HaN7oQOlGwpSA]]></FromUserName>    发送方帐号（一个OpenID）
     *     <CreateTime>1607526638</CreateTime>  消息创建时间 （整型）
     *     <MsgType><![CDATA[text]]></MsgType>  消息类型，文本为text
     *     <Content><![CDATA[测试语句]]></Content>   文本消息内容
     *     <MsgId>23014249720062552</MsgId>    消息id，64位整型
     * </xml>
     * JSON:
     * {"xml":{"CreateTime":1607530477,"Latitude":39.737999,"ToUserName":"gh_da292587e6af","MsgType":"event","Longitude":116.542847,"Precision":15,"Event":"LOCATION","FromUserName":"olj7y6D2pBjilT5HaN7oQOlGwpSA"}}
     * @param request 请求体
     * @param response 响应体
     */
    @PostMapping("/")
    public void sendMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = MsgHandleUtil.analysisMsg(HttpUtil.doPost(request));
        log.info("消息数据：\n" + jsonObject);
        //此消息类型用于了解接收到的消息类型
        String msgType = jsonObject.getByPath("xml.MsgType").toString();
        if (msgType.equals(RequestMsgType.EVENT)) {
            log.info("接收事件推送");
            String event = jsonObject.getByPath("xml.Event").toString();
            if (event.equals(EventMsgType.SUBSCRIBE)){
                Object eventKey = jsonObject.getByPath("xml.EventKey");
                if (eventKey == null) {
                    log.info("订阅事件推送.");
                } else {
                    log.info("用户未关注时，进行关注后的事件推送.");
                }
                return;
            }
            if (event.equals(EventMsgType.UNSUBSCRIBE)){
                log.info("取消订阅事件推送.");
                return;
            }
            if (event.equals(EventMsgType.SCAN)){
                log.info("用户已关注时的事件推送.");
                return;
            }
            if (event.equals(EventMsgType.LOCATION)){
                log.info("上报地理位置事件推送.");
                return;
            }
            if (event.equals(EventMsgType.CLICK)){
                log.info("自定义菜单事件推送.");
                return;
            }
            if (event.equals(EventMsgType.VIEW)){
                log.info("点击菜单跳转链接时的事件推送.");
                return;
            }
        }
        log.info("接收普通消息");
        String returnContent;
        //需要返回的是文本消息做如下处理
        if (msgType.equals(ResponseMsgType.TEXT)) {
            String content = jsonObject.getByPath("xml.Content").toString();
            if (content.equals("1")) {
                returnContent = "大吉大利,今晚吃鸡.(全匹配)";
            } else if (content.equals("2")) {
                returnContent = "落地成盒.(全匹配)";
            } else if (content.contains("1")) {
                returnContent = "大吉大利,今晚吃鸡(半匹配).";
            } else if (content.contains("2")) {
                returnContent = "落地成盒.(半匹配)";
            } else {
                returnContent = "啥也没有.";
            }
        } else {
            returnContent = "其他消息类型";
        }


        Map<String, Object> msgData = new HashMap<>();
        msgData.put("content", returnContent);
        //此消息类型用于明确回复的消息类型
        String returnTextXml = MsgHandleUtil.encapsulationMsg(ResponseMsgType.TEXT, jsonObject, msgData);
        HttpUtil.sendMsg(response, returnTextXml);
    }

    /**
     * 获取微信请求全局唯一凭据
     * @return 微信唯一凭据
     */
    @GetMapping("/token")
    public String token () {
        return WxUtil.getToken(appID, appsecret);
    }

    /**
     * 检查服务器延迟（ping or other）
     * @return dns等返回值
     */
    @GetMapping("/check")
    public JSON check () {
        return WxUtil.check();
    }
}
