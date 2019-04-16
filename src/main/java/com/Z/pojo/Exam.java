package com.Z.pojo;

import javax.persistence.*;

public class Exam {
    @Id
    private String id;

    private String ename;

    private String time;

    private String description;

    @Column(name = "account_id")
    private String accountId;

    /**
     * 1为开启考试，0为关闭考试
     */
    @Column(name = "e_switch")
    private Byte eSwitch;

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
     * @return ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * @param ename
     */
    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return account_id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取1为开启考试，0为关闭考试
     *
     * @return e_switch - 1为开启考试，0为关闭考试
     */
    public Byte geteSwitch() {
        return eSwitch;
    }

    /**
     * 设置1为开启考试，0为关闭考试
     *
     * @param eSwitch 1为开启考试，0为关闭考试
     */
    public void seteSwitch(Byte eSwitch) {
        this.eSwitch = eSwitch;
    }
}