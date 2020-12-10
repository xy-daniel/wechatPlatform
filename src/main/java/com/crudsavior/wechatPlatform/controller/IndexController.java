package com.crudsavior.wechatPlatform.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.crudsavior.wechatPlatform.utils.HttpUtil;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return 随机字符串 或者 error
     */
    @GetMapping("/")
    public String index(String signature, String timestamp, String nonce, String echostr) {
        log.info("接收到的参数：1、" + signature + " 2、" + timestamp + " 3、" + nonce + " 4、" + echostr);
        log.info("token：" + token);
        return WxUtil.checkSign(signature, timestamp, nonce, token)?echostr:"error";
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
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param openid 用户的微信id
     * @return 暂不确定
     */
    @PostMapping("/")
    public String getMsg(HttpServletRequest request, HttpServletResponse response, String signature, String timestamp, String nonce, String openid) throws IOException {
        log.info("接收到的参数：1、" + signature + " 2、" + timestamp + " 3、" + nonce + " 4、" + openid);
        boolean checkSign = WxUtil.checkSign(signature, timestamp, nonce, token);
        if (!checkSign) {
            return "error";
        }
        String xmlData = HttpUtil.doPost(request, response);
        log.info("接收到的消息数据：\n" + xmlData);
        JSONObject jsonObject = XML.toJSONObject(xmlData);
        log.info("用户发送的相关数据：\n" + jsonObject);
        return jsonObject.getByPath("xml.MsgType").toString();
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
