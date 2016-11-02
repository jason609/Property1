package com.ctrl.android.property.eric.entity;

import java.util.List;
import java.util.Map;

/**
 * 周围商家(服务)
 * Created by Eric on 2015/10/21.
 */
public class ShopService {

    /**分类名称*/
    private String category;
    /**分类 图标地址*/
    private String imgUrl;
    /**分类内容*/
    private List<Map<String,String>> listMap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Map<String, String>> getListMap() {
        return listMap;
    }

    public void setListMap(List<Map<String, String>> listMap) {
        this.listMap = listMap;
    }
}
