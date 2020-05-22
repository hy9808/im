package com.yj.im.project.entity.mongoEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 群消息实体
 */
public class ChatGroupMessage implements Serializable {

    private String id; //消息id

    private Long userId; //谁发的

    private Long toGroupId; //发送在哪个群

    private String message; //消息内容

    private Integer msgType; //消息类型

    private Integer delete; //是否删除(1.已删除 0.未删除)

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public ChatGroupMessage setId(String id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatGroupMessage setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getToGroupId() {
        return toGroupId;
    }

    public ChatGroupMessage setToGroupId(Long toGroupId) {
        this.toGroupId = toGroupId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatGroupMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public ChatGroupMessage setMsgType(Integer msgType) {
        this.msgType = msgType;
        return this;
    }

    public Integer getDelete() {
        return delete;
    }

    public ChatGroupMessage setDelete(Integer delete) {
        this.delete = delete;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public ChatGroupMessage setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public ChatGroupMessage setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
