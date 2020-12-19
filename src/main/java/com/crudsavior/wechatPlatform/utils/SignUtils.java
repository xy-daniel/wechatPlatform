package com.crudsavior.wechatPlatform.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

public class SignUtils {

    @Value("${com.crudsavior.url}")
    private static String URL;

    /**
     * 生成签名
     * @param timestamp 时间戳字符串
     * @param noncestr 随机字符串
     * @param url 短链接
     * @return 签名字符串
     */
    public static String fetchSign (String timestamp, String noncestr, String url) {
        String ticket = WxUtil.fetchTicket();

        String[] paramsArr = {
                "jsapi_ticket=" + ticket,
                "noncestr=" + noncestr,
                "timestamp" + timestamp,
                "url=" + URL + url
        };
        Arrays.sort(paramsArr);
        return StringUtils.join(paramsArr, "&");
    }
}
