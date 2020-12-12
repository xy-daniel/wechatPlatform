package com.crudsavior.wechatPlatform.utils;

import cn.hutool.json.*;
import com.crudsavior.wechatPlatform.status.ResponseMsgType;

import java.util.Map;

/**
 * MsgHandleUtil
 *
 * @author arctic
 * @date 2020/12/11
 **/
public class MsgHandleUtil {

    /**
     * 将xml字符串解析成JSONObject
     * @param xmlStr xml数据
     * @return JSONObject
     */
    public static JSONObject analysisMsg (String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }

    /**
     * 消息封装
     * @param msgType 回复的消息类型
     * @param jsonData 回复的基础数据
     * @param msgData 回复的消息内容
     * @return 封装完成的xml格式字符串消息
     */
    public static String encapsulationMsg (String msgType, String openid, JSONObject jsonData, Map<String, Object> msgData) {
        //发送用户
        String fromUserName = jsonData.getByPath("xml.ToUserName").toString();
        //消息创建时间
        String createTime = jsonData.getByPath("xml.CreateTime").toString();
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
