package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (ChatFriendGrouping)实体类
 *
 * @author makejava
 * @since 2020-04-07 09:38:50
 */
public class ChatFriendGrouping implements Serializable {
    private static final long serialVersionUID = -77191716370024989L;
    /**
    * 好友表主键
    */
    private Long friendGroupId;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 分组名称
    */
    private String groupName;
    /**
    * 创建时间 yyyy-MM-dd hh:mm:ss
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


    public Long getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(Long friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}