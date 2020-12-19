package com.crudsavior.wechatPlatform.controller;

import com.crudsavior.wechatPlatform.utils.SignUtils;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.UUID;

/**
 * TestController
 *
 * @author arctic
 * @date 2020/12/17
 **/
@Controller
public class TestController {

    @GetMapping("/admin/test")
    public String test(Model model) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String noncestr = UUID.randomUUID().toString();
        String signStr = SignUtils.fetchSign(timestamp, noncestr, "/admin/test");
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("signStr", signStr);
        return "test";
    }

}
