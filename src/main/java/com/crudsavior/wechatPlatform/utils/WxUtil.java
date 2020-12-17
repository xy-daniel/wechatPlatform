package com.crudsavior.wechatPlatform.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.crudsavior.wechatPlatform.cache.TokenCache;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
        String url = "https://api.weixin.qq.com/cgi-bin/callback/check?access_token=" + fetchToken();
        Map<String, Object> data = new HashMap<>();
        data.put("action", "all");
        data.put("check_operator", "DEFAULT");
        System.out.println("WxUtil.check 请求地址：" + url);
        System.out.println("WxUtil.check 请求参数：" + data.toString());
        return HttpUtil.postToJson(url, data);
    }

    /**
     * 创建菜单
     * {
     *      "button":[
     *      {
     *           "type":"click",
     *           "name":"今日歌曲",
     *           "key":"V1001_TODAY_MUSIC"
     *       },
     *       {
     *            "name":"菜单",
     *            "sub_button":[
     *            {
     *                "type":"view",
     *                "name":"搜索",
     *                "url":"http://www.soso.com/"
     *             },
     *             {
     *                  "type":"miniprogram",
     *                  "name":"wxa",
     *                  "url":"http://mp.weixin.qq.com",
     *                  "appid":"wx286b93c14bbf93aa",
     *                  "pagepath":"pages/lunar/index"
     *              },
     *             {
     *                "type":"click",
     *                "name":"赞一下我们",
     *                "key":"V1001_GOOD"
     *             }]
     *        }]
     *  }
     * @return JSON
     * @param jsonObject 创建按钮的json
     */
    public static String createMenu (AtomicReference<JSONObject> jsonObject) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + fetchToken();
        return HttpUtil.strPost(url, jsonObject.toString());
    }

    /**
     * 删除菜单
     * @return 返回值
     */
    public static String deleteMenu () {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + fetchToken();
        return HttpUtil.simplestGet(url);
    }

    /**
     * 查询菜单
     * @return 查询值
     */
    public static String getMenu () {
        String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=" + fetchToken();
        return HttpUtil.simplestGet(url);
    }

    public static String createConditionMenu (AtomicReference<JSONObject> jsonObject) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=" + fetchToken();
        return HttpUtil.strPost(url, jsonObject.toString());
    }

    public static String deleteConditionMenu(String menuidJson) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=" + fetchToken();
        return HttpUtil.strPost(url, menuidJson);
    }

    public static String trymatch(String useridJson) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=" + fetchToken();
        return HttpUtil.strPost(url, useridJson);
    }

    public static String getConditionMenu() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + fetchToken();
        System.out.println(fetchToken());
        return HttpUtil.simplestGet(url);

    }
}
