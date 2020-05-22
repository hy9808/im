package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户和群关联表(ChatUserGroup)实体类
 *
 * @author makejava
 * @since 2020-04-13 18:03:07
 */
public class ChatUserGroup implements Serializable {
    private static final long serialVersionUID = 135151179948285090L;

    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 群id
    */
    private Long groupId;
    /**
    * 用户群昵称
    */
    private String userGroupRemarks;
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

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头像
     */
    private String image;

    /**
     * 备注
     */
    private String remarks;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public ChatUserGroup setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ChatUserGroup setImage(String image) {
        this.image = image;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ChatUserGroup setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ChatUserGroup setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatUserGroup setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public ChatUserGroup setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getUserGroupRemarks() {
        return userGroupRemarks;
    }

    public ChatUserGroup setUserGroupRemarks(String userGroupRemarks) {
        this.userGroupRemarks = userGroupRemarks;
        return this;
    }

    public String getExpand() {
        return expand;
    }

    public ChatUserGroup setExpand(String expand) {
        this.expand = expand;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChatUserGroup setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatUserGroup setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChatUserGroup setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

}