package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 交友信息表(UserDatingMessage)实体类
 *
 * @author makejava
 * @since 2020-04-07 10:42:59
 */
public class UserDatingMessage implements Serializable {
    private static final long serialVersionUID = 553067669104456293L;
    /**
    * 交友信息表ID
    */
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 交友昵称
    */
    private String nikeName;
    /**
    * 生日
    */
    private Date birthday;
    /**
    * 所在省市
    */
    private String livingCity;
    /**
    * 工作性质
    */
    private String workNature;
    /**
    * 工作收入
    */
    private String wordIncome;
    /**
    * 用户性别（0保密，1男，2女）
    */
    private Integer sex;
    /**
    * 婚况
    */
    private Integer marriageStatus;
    /**
    * 身高（单位：毫米mm）
    */
    private Integer height;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 体重（单位：克g）
    */
    private Integer weight;
    /**
    * 爱好
    */
    private String hobby;
    /**
    * 特长
    */
    private String specialty;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 微信号
    */
    private String wechat;
    /**
    * 个性签名
    */
    private String customSign;
    /**
    * 状态（0逻辑删除、1数据有效）
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改时间
    */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public String getWordIncome() {
        return wordIncome;
    }

    public void setWordIncome(String wordIncome) {
        this.wordIncome = wordIncome;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Integer marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCustomSign() {
        return customSign;
    }

    public void setCustomSign(String customSign) {
        this.customSign = customSign;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}