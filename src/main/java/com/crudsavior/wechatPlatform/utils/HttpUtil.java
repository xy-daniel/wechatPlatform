package com.crudsavior.wechatPlatform.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpUtil
 *
 * @author arctic
 * @date 2020/12/9
 **/
public class HttpUtil {

    /**
     * 最简单的get请求
     * @param url 请求地址
     * @return 请求结果
     */
    public static String simplestGet (String url) {
        return cn.hutool.http.HttpUtil.get(url, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 转换为JSONObject的post请求
     * @return 返回值
     */
    static JSON postToJson (String url, Map<String, String> data) {
        return JSONUtil.parse(HttpRequest.post(url).body(String.valueOf(JSONUtil.parse(data))).execute().body());
    }
//
//    static JSONObject postNotHeader(String url, HashMap form){
//        new JSONObject(
//                HttpRequest.post(url).form(form).timeout(20000).execute().body()
//        )
//    }

    public static String doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream stream = request.getInputStream();
        byte[] b = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = stream.read(b)) != -1) {
            sb.append(new String(b, 0, len));
        }
        return sb.toString();
    }
}
