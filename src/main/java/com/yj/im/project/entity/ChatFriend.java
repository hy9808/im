package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (ChatFriend)实体类
 *
 * @author makejava
 * @since 2020-04-07 09:38:24
 */
public class ChatFriend implements Serializable {
    private static final long serialVersionUID = -27002057993662232L;
    /**
     * 主键id
     */
    private Long friendId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 朋友id
     */
    private Long userFriendId;
    /**
     * 朋友昵称
     */
    private String friendName;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 聊天分组表id
     */
    private Long friendGroupId;
    /**
     * 添加时间 yyyy-MM-dd hh:mm:ss
     */
    private Date createTime;
    /**
     * 更新时间 yyyy-MM-dd hh:mm:ss
     */
    private Date updateTime;
    /**
     * 状态（0逻辑删除、1数据有效）
     */
    private Integer status;
    /**
     * 头像
     */
    private String image;
    /**
     * 总数
     */
    private String count;
    /**
     *账号
     */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImage() { return image; }

    public ChatFriend setImage(String image) {this.image = image;return this;}

    public Long getFriendId() {
        return friendId;
    }

    public ChatFriend setFriendId(Long friendId) {
        this.friendId = friendId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatFriend setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getUserFriendId() {
        return userFriendId;
    }

    public ChatFriend setUserFriendId(Long userFriendId) {
        this.userFriendId = userFriendId;
        return this;
    }

    public String getFriendName() {
        return friendName;
    }

    public ChatFriend setFriendName(String friendName) {
        this.friendName = friendName;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ChatFriend setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Long getFriendGroupId() {
        return friendGroupId;
    }

    public ChatFriend setFriendGroupId(Long friendGroupId) {
        this.friendGroupId = friendGroupId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatFriend setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChatFriend setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChatFriend setStatus(Integer status) {
        this.status = status;
        return this;
    }

}