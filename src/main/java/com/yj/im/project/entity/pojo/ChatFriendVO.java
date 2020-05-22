package com.yj.im.project.entity.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author liyongchang
 * @Date2020/4/7
 */
@ApiModel(description = "视图对象")
public class ChatFriendVO implements Serializable {

    private static final long serialVersionUID = 654987987;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",name = "userId", required = true)
    private Long userId;

    /**
     * 用户昵称ck
     */
    @ApiModelProperty(value = "用户昵称", name = "nickName", required = true)
    private String nickName;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", name = "sex", required = true)
    private Integer sex;

    /**
     * 用户所在地区
     */
    @ApiModelProperty(value = "用户地区", name = "livingCity", required = true)
    private String livingCity;

    /**
     * 用户个性签名
     */
    @ApiModelProperty(value = "用户签名", name = "customSign", required = false)
    private String customSign;

    /**
     * 登陆账号  亿家号
     */
    @ApiModelProperty(value = "亿家号", name = "account", required = false)
    private String account;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "用户电话", name = "phone", required = false)
    private String phone;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像", name = "image", required = false)
    private String image;

    private Integer requestStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks", required = false)
    private String remarks;

    @ApiModelProperty(value = "好友状态", name = "flag", required = false)
    private Integer flag = 0; //好友标识 1好友 0不是好友


    public ChatFriendVO() {
    }

    public ChatFriendVO(Long userId, String nickName, Integer sex, String livingCity, String customSign, String account, String image, String phone, String remarks) {
        this.userId = userId;
        this.nickName = nickName;
        this.sex = sex;
        this.livingCity = livingCity;
        this.customSign = customSign;
        this.account = account;
        this.image = image;
        this.phone = phone;
        this.remarks = remarks;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nikeName) {
        this.nickName = nikeName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getCustomSign() {
        return customSign;
    }

    public void setCustomSign(String customSign) {
        this.customSign = customSign;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
