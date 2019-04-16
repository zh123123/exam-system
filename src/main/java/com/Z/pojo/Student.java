package com.Z.pojo;

import javax.persistence.*;

public class Student {
    @Id
    private String id;

    private String snum;

    private String name;

    private String img;

    private String room;

    @Column(name = "exam_id")
    private String examId;

    /**
     * 验证图片
     */
    @Column(name = "reg_img")
    private String regImg;

    @Column(name = "reg_status")
    private Byte regStatus;

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
     * @return snum
     */
    public String getSnum() {
        return snum;
    }

    /**
     * @param snum
     */
    public void setSnum(String snum) {
        this.snum = snum;
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
     * 获取验证图片
     *
     * @return reg_img - 验证图片
     */
    public String getRegImg() {
        return regImg;
    }

    /**
     * 设置验证图片
     *
     * @param regImg 验证图片
     */
    public void setRegImg(String regImg) {
        this.regImg = regImg;
    }

    /**
     * @return reg_status
     */
    public Byte getRegStatus() {
        return regStatus;
    }

    /**
     * @param regStatus
     */
    public void setRegStatus(Byte regStatus) {
        this.regStatus = regStatus;
    }
}