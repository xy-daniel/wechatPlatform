package com.crudsavior.wechatPlatform.service;

import com.crudsavior.wechatPlatform.model.RespModel;

/**
 * MenuService
 *
 * @author arctic
 * @date 2020/12/16
 **/

public interface MenuService {

    /**
     * 创建自定义菜单
     * @return 响应值
     */
    RespModel createMenu();

    /**
     * 创建个性化菜单
     * @return 响应值
     */
    String createConditionMenu();

    /**
     * 删除个性化菜单
     * @param menuidJson 创建时返回的menuid
     * @return 响应值
     */
    RespModel deleteConditionMenu(String menuidJson);

    String trymatch(String useridJson);

    String getConditionMenu();
}
