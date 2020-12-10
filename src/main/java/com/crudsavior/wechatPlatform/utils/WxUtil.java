package com.crudsavior.wechatPlatform.utils;

import cn.hutool.json.JSON;
import com.crudsavior.wechatPlatform.cache.TokenCache;
import com.crudsavior.wechatPlatform.controller.IndexController;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * WxUtil
 *
 * @author arctic
 * @date 2020/12/9
 **/
public class WxUtil {

    public static String BASE_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";

    /**
     * 微信服务器校验
     * @param signature 微信服务器校验字符串
     * @param paraStr 微信服务器+token数组
     * @return 是否来自微信服务器
     */
    public static boolean checkSign (String signature, String... paraStr) {
        Arrays.sort(paraStr);
        StringBuilder content = new StringBuilder();
        for (String string : paraStr) {
            content.append(string);
        }
        return DigestUtils.shaHex(content.toString()).equalsIgnoreCase(signature.toUpperCase());
    }

    /**
     * 获取微信请求全局token
     * 错误请求码请查阅：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     * @return success:{"access_token":"ACCESS_TOKEN","expires_in":7200},fail:{"errcode":40013,"errmsg":"invalid appid"}
     */
    public static String getToken (String appID, String appsecret) {
        String url = BASE_URL + "appid="+ appID +"&secret="+ appsecret;
        return HttpUtil.simplestGet(url);
    }

    /**
     * 直接从全局变量中获取可以使用的token
     * @return token
     */
    public static String fetchToken () {
        return TokenCache.tokenMap.get("token").toString();
    }

    /**
     * 微信服务器网络监测
     * @return 返回结果如下：
     * {
     *     "ping": [
     *         {
     *             "package_loss": "100%",
     *             "ip": "3.13.191.225",
     *             "from_operator": "DEFAULT",
     *             "time": ""
     *         }
     *     ],
     *     "dns": [
     *         {
     *             "real_operator": "DEFAULT",
     *             "ip": "3.13.191.225"
     *         }
     *     ]
     * }
     */
    public static JSON check () {
        String url = "https://api.weixin.qq.com/cgi-bin/callback/check?access_token=" + TokenCache.tokenMap.get("token");
        Map<String, String> data = new HashMap<>();
        data.put("action", "all");
        data.put("check_operator", "DEFAULT");
        System.out.println("WxUtil.check 请求地址：" + url);
        System.out.println("WxUtil.check 请求参数：" + data.toString());
        return HttpUtil.postToJson(url, data);
    }
}
