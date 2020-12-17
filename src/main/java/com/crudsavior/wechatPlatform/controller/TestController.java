package com.crudsavior.wechatPlatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TestController
 *
 * @author arctic
 * @date 2020/12/17
 **/
@Controller
public class TestController {

    @GetMapping("/admin/test")
    public String test () {
        return "test";
    }

}
