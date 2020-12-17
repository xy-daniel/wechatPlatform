package com.crudsavior.wechatPlatform.cache;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TokenCache
 *
 * @author arctic
 * @date 2020/12/9
 **/
@Component
public class TokenCache {

    public static Map<String, Object> tokenMap = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(TokenCache.class);

    @Value("${com.crudsavior.appID}")
    String appID;
    @Value("${com.crudsavior.appsecret}")
    String appsecret;

    @PostConstruct
    public void init () {
        int mapSize = tokenMap.size();
        if (mapSize == 0) {
            method();
        }else{
            long end = Long.parseLong(tokenMap.get("end").toString());
            if (System.currentTimeMillis() + 10*60*1000 > end) {
                log.info("10分钟之后token过期，重新获取.");
                method();
            } else {
                log.info("token还有超过10分钟的有效期.");
            }
        }

    }

    @PreDestroy
    public void destroy() {
        //系统运行结束
        log.info("系统结束运行");
    }

    /**
     * 每10分钟执行一次缓存
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void execute() {
        init();
    }

    public void method () {
        log.info("开始获取微信全局变量token");
        String tokenJsonStr = WxUtil.getToken(appID, appsecret);
        JSON json = JSONUtil.parse(tokenJsonStr);
        String token = json.getByPath("access_token").toString();
        tokenMap.put("token", token);
        String ticketJsonStr = WxUtil.geTicket(token);
        JSON ticketJson = JSONUtil.parse(ticketJsonStr);
        tokenMap.put("ticket", ticketJson.getByPath("ticket"));
        tokenMap.put("end", System.currentTimeMillis()+Integer.parseInt(json.getByPath("expires_in").toString())*1000);
        log.info("时间："+new Date()+",tokenMap："+tokenMap.toString());
    }
}
