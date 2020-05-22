package com.yj.im.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 系统级消息表读取表(ChatSysRead)实体类
 *
 * @author makejava
 * @since 2020-04-11 11:16:41
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatSysRead implements Serializable {
    private static final long serialVersionUID = 620675558832956880L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 读取的条数
     */
    private Integer readCount;
    /**
     * 读取消息类型 0.系统消息 1.订单消息
     */
    private Integer readType;


    public Long getId() {
        return id;
    }

    public ChatSysRead setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatSysRead setUserId(Long userId) {
        this.userId = userId;
        return this;

    }

    public Integer getReadCount() {
        return readCount;
    }

    public ChatSysRead setReadCount(Integer readCount) {
        this.readCount = readCount;
        return this;

    }

    public Integer getReadType() {
        return readType;
    }

    public ChatSysRead setReadType(Integer readType) {
        this.readType = readType;
        return this;

    }

}