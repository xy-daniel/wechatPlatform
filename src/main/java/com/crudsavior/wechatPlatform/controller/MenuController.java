package com.crudsavior.wechatPlatform.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.crudsavior.wechatPlatform.model.RespModel;
import com.crudsavior.wechatPlatform.service.MenuService;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MenuController
 *
 * @author arctic
 * @date 2020/12/15
 **/
@RestController
public class MenuController {

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 创建自定义菜单
     * @return 响应值
     */
    @PostMapping("/menu")
    public RespModel createMenu () {
        return menuService.createMenu();
    }

    /**
     * 删除自定义菜单
     * @return 响应值
     */
    @DeleteMapping("/menu")
    public RespModel deleteMenu () {
        return JSONUtil.toBean(WxUtil.deleteMenu(), RespModel.class);
    }

    /**
     * 查询自定义菜单
     * @return 响应值
     */
    @GetMapping("/menu")
    public JSONObject getMenu () {
        return JSONUtil.parseObj(WxUtil.getMenu());
    }

    /**
     * 创建个性化菜单
     * @return 响应值如下
     */
    @PostMapping("/menu/condition")
    public String createConditionMenu () {
        //{"menuid":435021629}
        //{"menuid":435021631}
        return menuService.createConditionMenu();
    }

    /**
     * 山吹个性化菜单
     * @return 响应值如下
     */
    @DeleteMapping("/menu/condition")
    public RespModel deleteConditionMenu () {
        /*
         * {
         *     "errcode": 0,
         *     "errmsg": "ok"
         * }
         */
        return menuService.deleteConditionMenu("{\"menuid\":435021626}");
    }

    /**
     * 测试个性化菜单匹配结果
     * @param user_id  可以是粉丝的OpenID，也可以是粉丝的微信号
     * @return 匹配到的菜单结果
     */
    @GetMapping("/menu/trymatch")
    public String trymatch (String user_id) {
        String useridJson = "{\"user_id\":\""+ user_id +"\"}";
        return menuService.trymatch(useridJson);
    }
}
