package com.ctrl.android.property.eric.entity;

/**
 * 调查详细
 * Created by Eric on 2015/11/4.
 */
public class SurveyDetail {

    private String id;//问卷问题ID
    private String question;//问卷问题
    private String a;//选项a
    private int answerA;//选中状态（0：未选中、1：选中）
    private String b;//选项b
    private int answerB;//选中状态（0：未选中、1：选中）
    private String c;//选项c
    private int answerC;//选中状态（0：未选中、1：选中）
    private int chooseType;//选择类型（0：单选、1：多选）注：问卷类型是"0"(调查问卷)时，该处只能是单选；问卷类型是"1"(投票)时，该处可以是多选

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getAnswerA() {
        return answerA;
    }

    public void setAnswerA(int answerA) {
        this.answerA = answerA;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public int getAnswerB() {
        return answerB;
    }

    public void setAnswerB(int answerB) {
        this.answerB = answerB;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public int getAnswerC() {
        return answerC;
    }

    public void setAnswerC(int answerC) {
        this.answerC = answerC;
    }

    public int getChooseType() {
        return chooseType;
    }

    public void setChooseType(int chooseType) {
        this.chooseType = chooseType;
    }
}
