package com.ctrl.android.property.eric.entity;

/**
 *
 * Created by Eric on 2015/11/13.
 */
public class Item {

    public Item(String id,String name,boolean check){

        this.id = id;
        this.name = name;
        this.check = check;

    }

    private String id;
    private int flg;
    private String name;
    private boolean check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFlg() {
        return flg;
    }

    public void setFlg(int flg) {
        this.flg = flg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
