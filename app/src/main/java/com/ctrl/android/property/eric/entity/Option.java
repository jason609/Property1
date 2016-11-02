package com.ctrl.android.property.eric.entity;

/**
 * 投票选择
 * Created by Eric on 2015/11/5.
 */
public class Option {

    private int optionASum;//选项a的投票总数
    private int optionBSum;//选项b的投票总数
    private int optionCSum;//选项c的投票总数

    public int getOptionASum() {
        return optionASum;
    }

    public void setOptionASum(int optionASum) {
        this.optionASum = optionASum;
    }

    public int getOptionBSum() {
        return optionBSum;
    }

    public void setOptionBSum(int optionBSum) {
        this.optionBSum = optionBSum;
    }

    public int getOptionCSum() {
        return optionCSum;
    }

    public void setOptionCSum(int optionCSum) {
        this.optionCSum = optionCSum;
    }
}
