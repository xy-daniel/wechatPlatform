package com.crudsavior.wechatPlatform.model;

import java.util.List;

/**
 * ButtonModel
 *
 * @author arctic
 * @date 2020/12/16
 **/
public class ButtonModel {
    private String type;
    private String name;
    private String key;
    private String url;
    private List<ButtonModel> sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ButtonModel> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<ButtonModel> sub_button) {
        this.sub_button = sub_button;
    }

    public ButtonModel() {
    }

    public ButtonModel(String type, String name, String thirdStr) {
        this.type = type;
        this.name = name;
        if (thirdStr.startsWith("http")) {
            this.url = thirdStr;
        } else {
            this.key = thirdStr;
        }
    }

    public ButtonModel(String name, List<ButtonModel> sub_button) {
        this.name = name;
        this.sub_button = sub_button;
    }

}
