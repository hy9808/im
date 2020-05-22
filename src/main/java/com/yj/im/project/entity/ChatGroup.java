package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 群组基础表(ChatGroup)实体类
 *
 * @author makejava
 * @since 2020-04-13 18:02:42
 */
public class ChatGroup implements Serializable {
    private static final long serialVersionUID = -78651166768000297L;
    /**
    * 群主键id
    */
    private Long id;
    /**
    * 群名
    */
    private String groupName;
    /**
    * 群主
    */
    private Long groupCreateUserId;
    /**
    * 群公告
    */
    private String groupAnnouncement;
    /**
    * 群头像
    */
    private String groupImage;
    /**
    * 群简介
    */
    private String groupIntroduction;
    /**
    * 拓展字段
    */
    private String expand;
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

    public ChatGroup setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public ChatGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Long getGroupCreateUserId() {
        return groupCreateUserId;
    }

    public ChatGroup setGroupCreateUserId(Long groupCreateUserId) {
        this.groupCreateUserId = groupCreateUserId;
        return this;
    }

    public String getGroupAnnouncement() {
        return groupAnnouncement;
    }

    public ChatGroup setGroupAnnouncement(String groupAnnouncement) {
        this.groupAnnouncement = groupAnnouncement;
        return this;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public ChatGroup setGroupImage(String groupImage) {
        this.groupImage = groupImage;
        return this;
    }

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public ChatGroup setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
        return this;
    }

    public String getExpand() {
        return expand;
    }

    public ChatGroup setExpand(String expand) {
        this.expand = expand;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChatGroup setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatGroup setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChatGroup setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

}