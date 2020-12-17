package com.crudsavior.wechatPlatform.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.crudsavior.wechatPlatform.model.ButtonModel;
import com.crudsavior.wechatPlatform.model.MatchruleModel;
import com.crudsavior.wechatPlatform.model.RespModel;
import com.crudsavior.wechatPlatform.service.MenuService;
import com.crudsavior.wechatPlatform.utils.WxUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * MenuServiceImpl
 *
 * @author arctic
 * @date 2020/12/16
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Override
    public RespModel createMenu() {
        Map<String, List<ButtonModel>> data = new HashMap<>();
        List<ButtonModel> button = new ArrayList<>();
        button.add(new ButtonModel("click", "今日歌曲", "V1001_TODAY_MUSIC"));
        List<ButtonModel> sub_button1 = new ArrayList<>();
        sub_button1.add(new ButtonModel("view", "搜索", "http://www.soso.com/"));
        sub_button1.add(new ButtonModel("scancode_waitmsg", "扫码带提示", "rselfmenu_0_0"));
        sub_button1.add(new ButtonModel("scancode_push", "扫码推事件", "rselfmenu_0_1"));
        button.add(new ButtonModel("菜单", sub_button1));
        List<ButtonModel> sub_button2 = new ArrayList<>();
        sub_button2.add(new ButtonModel("pic_sysphoto", "系统拍照发图", "rselfmenu_1_0"));
        sub_button2.add(new ButtonModel("pic_photo_or_album", "拍照或相册发图", "rselfmenu_1_1"));
        sub_button2.add(new ButtonModel("pic_weixin", "微信相册发图", "rselfmenu_1_2"));
        sub_button2.add(new ButtonModel("location_select", "发送位置", "rselfmenu_2_0"));
        button.add(new ButtonModel("发图", sub_button2));
        data.put("button", button);
        AtomicReference<JSONObject> jsonObject = new AtomicReference<>(new JSONObject(data, true));
        return JSONUtil.toBean(WxUtil.createMenu(jsonObject), RespModel.class);

    }

    @Override
    public String createConditionMenu() {
        Map<String, Object> data = new HashMap<>();
        List<ButtonModel> button = new ArrayList<>();
        button.add(new ButtonModel("click", "今日歌曲", "V1001_TODAY_MUSIC"));
        List<ButtonModel> sub_button1 = new ArrayList<>();
        sub_button1.add(new ButtonModel("view", "搜索", "http://www.soso.com/"));
        sub_button1.add(new ButtonModel("scancode_waitmsg", "扫码带提示", "rselfmenu_0_0"));
        sub_button1.add(new ButtonModel("scancode_push", "扫码推事件", "rselfmenu_0_1"));
        button.add(new ButtonModel("菜单", sub_button1));
//        List<ButtonModel> sub_button2 = new ArrayList<>();
//        sub_button2.add(new ButtonModel("pic_sysphoto", "系统拍照发图", "rselfmenu_1_0"));
//        sub_button2.add(new ButtonModel("pic_photo_or_album", "拍照或相册发图", "rselfmenu_1_1"));
//        sub_button2.add(new ButtonModel("pic_weixin", "微信相册发图", "rselfmenu_1_2"));
//        sub_button2.add(new ButtonModel("location_select", "发送位置", "rselfmenu_2_0"));
//        button.add(new ButtonModel("发图", sub_button2));
        data.put("button", button);
        data.put("matchrule", new MatchruleModel("3", "1", "中国", "山东", "聊城", "2", "zh_CN"));
        AtomicReference<JSONObject> jsonObject = new AtomicReference<>(new JSONObject(data, true));
        return WxUtil.createConditionMenu(jsonObject);
    }

    @Override
    public RespModel deleteConditionMenu(String menuidJson) {
        return JSONUtil.toBean(WxUtil.deleteConditionMenu(menuidJson), RespModel.class);
    }

    @Override
    public String trymatch(String useridJson) {
        return WxUtil.trymatch(useridJson);
    }

    @Override
    public String getConditionMenu() {
        return WxUtil.getConditionMenu();
    }
}
