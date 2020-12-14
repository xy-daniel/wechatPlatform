package com.crudsavior.wechatPlatform.utils;

import cn.hutool.json.*;
import com.crudsavior.wechatPlatform.status.EventMsgType;
import com.crudsavior.wechatPlatform.status.RequestMsgType;
import com.crudsavior.wechatPlatform.status.ResponseMsgType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * MsgHandleUtil
 *
 * @author arctic
 * @date 2020/12/11
 **/
public class MsgHandleUtil {

    private static final Logger log = LoggerFactory.getLogger(MsgHandleUtil.class);


    /**
     * 将xml字符串解析成JSONObject
     * @param xmlStr xml数据
     * @return JSONObject
     */
    public static JSONObject analysisMsg (String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }

    /**
     * 封装用于返回的基础数据
     * @param jsonObject 接收到的用户数据
     * @return 封装后的Map数据
     */
    public static Map<String, Object> getMsgData (JSONObject jsonObject) {
        //消息封装仅供测试，真实环境可以创建实例类，然后其他实体类继承基础实体类
        Map<String, Object> msgData = new HashMap<>();
        msgData.put("openid", jsonObject.getByPath("xml.FromUserName").toString());
        msgData.put("fromUserName", jsonObject.getByPath("xml.ToUserName").toString());
        msgData.put("msgType", ResponseMsgType.TEXT);
        String returnContent;
        String msgType = jsonObject.getByPath("xml.MsgType").toString();
        switch (msgType) {
            case RequestMsgType.EVENT:
                log.info("接收事件推送");
                String event = jsonObject.getByPath("xml.Event").toString();
                switch (event) {
                    case EventMsgType.SUBSCRIBE:
                        Object eventKey = jsonObject.getByPath("xml.EventKey");
                        if (eventKey == null) {
                            log.info("订阅事件推送.");
                            msgData.put("content", "订阅事件推送.");
                        } else {
                            log.info("用户未关注时，进行关注后的事件推送.");
                            msgData.put("content", "用户未关注时，进行关注后的事件推送.");
                        }
                        break;
                    case EventMsgType.UNSUBSCRIBE:
                        log.info("取消订阅事件推送.");
                        msgData.put("content", "取消订阅事件推送.");
                        break;
                    case EventMsgType.SCAN:
                        log.info("用户已关注时的事件推送.");
                        msgData.put("content", "用户已关注时的事件推送.");
                        break;
                    case EventMsgType.LOCATION:
                        log.info("上报地理位置事件推送.");
                        msgData.put("content", "上报地理位置事件推送.");
                        break;
                    case EventMsgType.CLICK:
                        log.info("自定义菜单事件推送.");
                        msgData.put("content", "自定义菜单事件推送.");
                        break;
                    case EventMsgType.VIEW:
                        log.info("点击菜单跳转链接时的事件推送.");
                        msgData.put("content", "点击菜单跳转链接时的事件推送.");
                        break;
                }
                break;
            case RequestMsgType.TEXT:
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
                msgData.put("content", returnContent);
                break;
            case RequestMsgType.IMAGE:
                msgData.put("msgType", ResponseMsgType.IMAGE);
                msgData.put("media_id", jsonObject.getByPath("xml.MediaId").toString());
                break;
            case RequestMsgType.VOICE:
                msgData.put("msgType", ResponseMsgType.VOICE);
                msgData.put("media_id", jsonObject.getByPath("xml.MediaId").toString());
                break;
            case RequestMsgType.VIDEO:
                msgData.put("msgType", ResponseMsgType.VIDEO);
                msgData.put("media_id", jsonObject.getByPath("xml.MediaId").toString());
                //下面这两个还真不能乱写
                msgData.put("title", "测试标题");
                msgData.put("description", "测试描述");
                break;
            case RequestMsgType.SHORT_VIDEO:
                returnContent = "小视频消息";
                msgData.put("content", returnContent);
                break;
            case RequestMsgType.LOCATION:
                returnContent = "地理位置消息";
                msgData.put("content", returnContent);
                break;
            case RequestMsgType.LINK:
                returnContent = "链接消息";
                msgData.put("content", returnContent);
                break;
        }
        return msgData;
    }

    /**
     * 消息封装
     * @param msgData 回复的消息内容
     * @return 封装完成的xml格式字符串消息
     */
    public static String encapsulationXMLMsg (Map<String, Object> msgData) {
        String msgType = (String) msgData.get("msgType");
        //接收用户
        String openid = (String) msgData.get("openid");
        //发送用户
        String fromUserName = (String) msgData.get("fromUserName");
        //消息创建时间
        long createTime = System.currentTimeMillis();
        boolean flag = false;
        StringBuilder xmlString = new StringBuilder(
                "<xml>" +
                    "<ToUserName><![CDATA[" + openid + "]]></ToUserName>" +
                    "<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>" +
                    "<CreateTime>" + createTime + "</CreateTime>" +
                    "<MsgType><![CDATA[" + msgType + "]]></MsgType>");
        //文本消息
        if (msgType.equals(ResponseMsgType.TEXT)) {
            flag = true;
            xmlString.append("<Content><![CDATA[").append(msgData.get("content").toString()).append("]]></Content>");
        }
        //图片消息
        if (msgType.equals(ResponseMsgType.IMAGE)) {
            flag = true;
            xmlString
                    .append("<Image>")
                    .append("<MediaId><![CDATA[").append(msgData.get("media_id").toString()).append("]]></MediaId>")
                    .append("</Image>");
        }
        //语音消息
        if (msgType.equals(ResponseMsgType.VOICE)) {
            flag = true;
            xmlString
                    .append("<Voice>")
                    .append("<MediaId><![CDATA[").append(msgData.get("media_id").toString()).append("]]></MediaId>")
                    .append("</Voice>");
        }
        //视频消息
        if (msgType.equals(ResponseMsgType.VIDEO)) {
            flag = true;
            xmlString
                    .append("<Video>")
                    .append("<MediaId><![CDATA[").append(msgData.get("media_id").toString()).append("]]></MediaId>")
                    .append("<Title><![CDATA[").append(msgData.get("title").toString()).append("]]></Title>")
                    .append("<Description><![CDATA[").append(msgData.get("description").toString()).append("]]></Description>")
                    .append("</Video>");
        }
        //音乐消息
        if (msgType.equals(ResponseMsgType.MUSIC)) {
            flag = true;
            xmlString
                    .append("<Music>")
                    .append("<Title><![CDATA[").append(msgData.get("title").toString()).append("]]></Title>")
                    .append("<Description><![CDATA[").append(msgData.get("description").toString()).append("]]></Description>")
                    .append("<MusicUrl><![CDATA[").append(msgData.get("musicUrl").toString()).append("]]></MusicUrl>")
                    .append("<HQMusicUrl><![CDATA[").append(msgData.get("hqMusicUrl").toString()).append("]]></HQMusicUrl>")
                    .append("<ThumbMediaId><![CDATA[").append(msgData.get("media_id").toString()).append("]]></ThumbMediaId>")
                    .append("</Music>");
        }
        //图文消息
        if (msgType.equals(ResponseMsgType.NEWS)) {
            flag = true;

            //图文消息的list组装方式
//            List<Map<String, String>> itemList = new ArrayList<>();
//            Map<String, String> itemMap = new HashMap<>();
//            itemMap.put("title","title");
//            itemMap.put("description","description");
//            itemMap.put("picurl","picurl");
//            itemMap.put("url","url");
//            itemList.add(itemMap);
            JSONArray items = JSONUtil.parseArray(msgData.get("items"));
            xmlString.append("<ArticleCount>").append(items.size()).append("</ArticleCount>");
            xmlString.append("<Articles>");
            for (Object item : items) {
                JSONObject jsonObject = JSONUtil.parseObj(item);
                xmlString
                        .append("<item>")
                        .append("<Title><![CDATA[").append(jsonObject.getStr("title")).append("]]></Title>")
                        .append("<Description><![CDATA[").append(jsonObject.getStr("description")).append("]]></Description>")
                        .append("<PicUrl><![CDATA[").append(jsonObject.getStr("picurl")).append("]]></PicUrl>")
                        .append("<Url><![CDATA[").append(jsonObject.getStr("url")).append("]]></Url>")
                        .append("</item>");
            }
            xmlString.append("</Articles>");
        }
        if (flag) {
            xmlString.append("</xml>");
            return xmlString.toString();
        }
        return "";
    }

}
