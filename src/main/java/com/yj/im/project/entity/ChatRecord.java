package com.yj.im.project.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 聊天记录表(ChatRecord)实体类
 *
 * @author makejava
 * @since 2020-04-07 16:14:52
 */
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = 489748139527994068L;
    /**
     * 记录表id
     */
    private Long id;

    private String mongoId;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 接收者用户ID
     */
    private Long recipientId;

    /**
     * 用户在群里的昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String img;

    /**
     * 消息名称
     */
    private String msgName;

    /**
     * 是否已读(0.已读 1.未读)
     */
    private Integer hasRead;
    /**
     * 是否删除(0.已删除 1.未删除)
     */
    private Integer hasDelete;
    /**
     * 消息类型(0.文字 1.图片,3.视频)
     */
    private Integer msgType;

    /**
     * 区分用户消息还是系统消息
     */
    private Integer sysMsgType;

    /**
     * 消息内容(如果是图片则为文件路径)
     */
    private String message;
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

    public String getMongoId() {
        return mongoId;
    }

    public ChatRecord setMongoId(String mongoId) {
        this.mongoId = mongoId;
        return this;
    }

    public String getImg() {
        return img;
    }

    public ChatRecord setImg(String img) {
        this.img = img;
        return this;
    }

    public String getMsgName() {
        return msgName;
    }

    public ChatRecord setMsgName(String msgName) {
        this.msgName = msgName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public ChatRecord setNickName(String nickName) {
        this.nickName = nickName;
        return this;

    }

    public Integer getSysMsgType() {
        return sysMsgType;
    }

    public ChatRecord setSysMsgType(Integer sysMsgType) {
        this.sysMsgType = sysMsgType;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ChatRecord setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatRecord setUserId(Long userId) {
        this.userId = userId;

        return this;

    }

    public Long getRecipientId() {
        return recipientId;
    }

    public ChatRecord setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
        return this;

    }

    public Integer getHasRead() {
        return hasRead;
    }

    public ChatRecord setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
        return this;

    }

    public Integer getHasDelete() {
        return hasDelete;
    }

    public ChatRecord setHasDelete(Integer hasDelete) {
        this.hasDelete = hasDelete;
        return this;

    }

    public Integer getMsgType() {
        return msgType;
    }

    public ChatRecord setMsgType(Integer msgType) {
        this.msgType = msgType;
        return this;

    }

    public String getMessage() {
        return message;
    }

    public ChatRecord setMessage(String message) {
        this.message = message;
        return this;

    }

    public Integer getStatus() {
        return status;
    }

    public ChatRecord setStatus(Integer status) {
        this.status = status;
        return this;

    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatRecord setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;

    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ChatRecord setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;

    }

}