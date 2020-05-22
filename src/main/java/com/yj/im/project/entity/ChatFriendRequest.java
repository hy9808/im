package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (ChatFriendRequest)实体类
 *
 * @author makejava
 * @since 2020-04-07 09:39:03
 */
public class ChatFriendRequest implements Serializable {
    private static final long serialVersionUID = -10747585940819113L;
    /**
    * 好友请求表id
    */
    private Long friendRequestId;
    /**
    * 请求者id
    */
    private Long requesterId;
    /**
    * 接收请求的用户id
    */
    private Long userId;
    /**
    * 请求内容
    */
    private String requestContent;
    /**
    * 请求状态  1:同意；2:拒绝 3:未处理
    */
    private Integer requestStatus;
    /**
    * 请求时间 yyyy-MM-dd hh:mm:ss
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

    public ChatFriendRequest() {
    }

    public ChatFriendRequest(Long friendRequestId, Long requesterId, Long userId, String requestContent, Integer requestStatus, Date createTime, Date updateTime, Integer status) {
        this.friendRequestId = friendRequestId;
        this.requesterId = requesterId;
        this.userId = userId;
        this.requestContent = requestContent;
        this.requestStatus = requestStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    public Long getFriendRequestId() {
        return friendRequestId;
    }

    public ChatFriendRequest setFriendRequestId(Long friendRequestId) {
        this.friendRequestId = friendRequestId;
        return this;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public ChatFriendRequest setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatFriendRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public ChatFriendRequest setRequestContent(String requestContent) {
        this.requestContent = requestContent;
        return this;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public ChatFriendRequest setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatFriendRequest setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChatFriendRequest setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChatFriendRequest setStatus(Integer status) {
        this.status = status;
        return this;
    }

}