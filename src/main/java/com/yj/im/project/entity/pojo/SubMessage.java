package com.yj.im.project.entity.pojo;

import java.io.Serializable;
import java.util.Date;

public class SubMessage implements Serializable {
    private Long userId;

    private Integer msgType;

    private String message;

    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public SubMessage setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public SubMessage setMsgType(Integer msgType) {
        this.msgType = msgType;
        return this;

    }

    public String getMessage() {
        return message;
    }

    public SubMessage setMessage(String message) {
        this.message = message;
        return this;

    }

    public Date getCreateTime() {
        return createTime;
    }

    public SubMessage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;

    }
}
