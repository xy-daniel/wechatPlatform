package com.crudsavior.wechatPlatform.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.crudsavior.wechatPlatform.status.RequestMsgType;
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
     * 从流中获取消息解析成JSONObject
     * @param xmlData xml数据
     * @return JSONObject
     */
    public static JSONObject analysisMsg (String xmlData) {
        return XML.toJSONObject(xmlData);
    }

    /**
     * 消息封装
     * @param msgType 回复的消息类型
     * @param jsonData 回复的基础数据
     * @param msgData 回复的消息内容
     * @return 封装完成的xml格式字符串消息
     */
    public static String encapsulationMsg (String msgType, JSONObject jsonData, Map<String, Object> msgData) {
        //发送用户
        String fromUserName = jsonData.getByPath("xml.ToUserName").toString();
        //接收用户
        String toUserName = jsonData.getByPath("xml.FromUserName").toString();
        //消息创建时间
        String createTime = jsonData.getByPath("xml.CreateTime").toString();
        //消息id(3天有效期)
        String msgId = jsonData.getByPath("xml.MsgId").toString();
        //文本消息
        boolean flag = false;
        String xmlString = "<xml>" +
                "<ToUserName><![CDATA["+ toUserName +"]]></ToUserName>" +
                "<FromUserName><![CDATA["+ fromUserName +"]]></FromUserName>" +
                "<CreateTime>"+ createTime +"</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>";
        if (msgType.equals(ResponseMsgType.TEXT)) {
            flag = true;
            xmlString += "<Content><![CDATA["+ msgData.get("content").toString() +"]]></Content>";
        }
        //图片消息
        if (msgType.equals(ResponseMsgType.IMAGE)) {
            flag = true;
            xmlString += "<Image>" +
                    "<MediaId><![CDATA[media_id]]></MediaId>" +
                    "</Image>";
        }
        //语音消息
        if (msgType.equals(ResponseMsgType.VOICE)) {
            flag = true;
            xmlString += "<Voice>" +
                    "<MediaId><![CDATA[media_id]]></MediaId>" +
                    "</Voice>";
        }
        //视频消息
        if (msgType.equals(ResponseMsgType.VIDEO)) {
            flag = true;
            xmlString += "<Video>" +
                    "<MediaId><![CDATA[media_id]]></MediaId>" +
                    "<Title><![CDATA[title]]></Title>" +
                    "<Description><![CDATA[description]]></Description>" +
                    "</Video>";
        }
        //音乐消息
        if (msgType.equals(ResponseMsgType.MUSIC)) {
            flag = true;
            xmlString += "<Music>" +
                    "<Title><![CDATA[TITLE]]></Title>" +
                    "<Description><![CDATA[DESCRIPTION]]></Description>" +
                    "<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>" +
                    "<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>" +
                    "<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>" +
                    "</Music>";
        }
        //图文消息
        if (msgType.equals(ResponseMsgType.NEWS)) {
            flag = true;
            xmlString += "<ArticleCount>1</ArticleCount>" +
                    "<Articles>" +
                    "<item>" +
                    " <Title><![CDATA[title1]]></Title>" +
                    " <Description><![CDATA[description1]]></Description>" +
                    " <PicUrl><![CDATA[picurl]]></PicUrl>" +
                    " <Url><![CDATA[url]]></Url>" +
                    "</item>" +
                    "</Articles>";
        }
        if (flag) {
            xmlString += "</xml>";
            return xmlString;
        }
        return "";
    }

}
