package com.crudsavior.wechatPlatform.interceptor;

import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * WxInterceptor
 * 微信服务器校验拦截器
 * @author arctic
 * @date 2020/12/10
 **/
public class WxInterceptor implements HandlerInterceptor {

    @Value("${com.crudsavior.token}")
    private String token;

    private final Logger log = LoggerFactory.getLogger(WxInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        log.info("拦截到的参数：1、" + signature + " 2、" + timestamp + " 3、" + nonce + "4、" + token);
        if (!WxUtil.checkSign(signature, timestamp, nonce, token)) {
            log.error("校验微信服务器失败,被拦截.");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("成功通过拦截器.");
    }
}
