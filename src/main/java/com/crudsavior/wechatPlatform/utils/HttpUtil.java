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
    public static JSON postToJson (String url, Map<String, Object> data) {
        return JSONUtil.parse(HttpRequest.post(url).body(String.valueOf(JSONUtil.parse(data))).execute().body());
    }

    public static String strPost (String url, String strData) {
        return HttpRequest.post(url).body(strData).execute().body();
    }

    /**
     * 从请求体的流中解析数据
     * @param request 请求体
     * @return 流数据
     * @throws IOException IO异常
     */
    public static String doPost(HttpServletRequest request) throws IOException {
        ServletInputStream stream = request.getInputStream();
        byte[] b = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = stream.read(b)) != -1) {
            sb.append(new String(b, 0, len));
        }
        stream.close();
        return sb.toString();
    }

    /**
     * 以响应体的方式回复内容
     * @param response 响应体
     * @param data 回复数据
     * @throws IOException IO异常
     */
    public static void sendMsg(HttpServletResponse response, String data) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(data);
    }
}
