package com.Z.pojo;

import java.util.Date;
import javax.persistence.*;

public class Teacher {
    @Id
    private String id;

    private String tnum;

    private String name;

    private String img;

    private String room;

    @Column(name = "exam_id")
    private String examId;

    @Column(name = "reg_img")
    private String regImg;

    /**
     * 最后登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return tnum
     */
    public String getTnum() {
        return tnum;
    }

    /**
     * @param tnum
     */
    public void setTnum(String tnum) {
        this.tnum = tnum;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return exam_id
     */
    public String getExamId() {
        return examId;
    }

    /**
     * @param examId
     */
    public void setExamId(String examId) {
        this.examId = examId;
    }

    /**
     * @return reg_img
     */
    public String getRegImg() {
        return regImg;
    }

    /**
     * @param regImg
     */
    public void setRegImg(String regImg) {
        this.regImg = regImg;
    }

    /**
     * 获取最后登录时间
     *
     * @return login_time - 最后登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param loginTime 最后登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}