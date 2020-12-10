package com.crudsavior.wechatPlatform.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil
 *
 * @author arctic
 * @date 2020/12/9
 **/
public class StringUtil {

    public static String matcher (String str) {
        Pattern r = Pattern.compile("<![CDATA[.*?]]>");
        Matcher m = r.matcher(str);
        return str.substring(m.start(), m.end());
    }

}
