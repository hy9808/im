package com.yj.im.project.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础信息表(SysUser)实体类
 *
 * @author makejava
 * @since 2020-04-06 12:04:07
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 266570941743783173L;
    /**
    * 用户ID
    */
    private Long id;
    /**
    * 登录账号
    */
    private String account;
    /**
    * 用户名称
    */
    private String name;
    /**
    * 用户手机号
    */
    private String phone;
    /**
    * 用户邮箱
    */
    private String email;
    /**
    * 登录密码
    */
    private String password;
    /**
    * 用户头像
    */
    private String image;
    /**
    * 状态（0逻辑删除、1数据有效）
    */
    private Object status;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
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