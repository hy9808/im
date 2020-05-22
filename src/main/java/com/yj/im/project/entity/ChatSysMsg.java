package com.yj.im.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.io.Serializable;

/**
 * 系统级消息记录(ChatSysMsg)实体类
 *
 * @author makejava
 * @since 2020-04-11 11:16:17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatSysMsg implements Serializable {
    private static final long serialVersionUID = 930825991606336291L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 消息类型 0.系统消息 1.订单消息
     */
    private Integer sysMsgType;
    /**
     * 消息类型(0.文字 1.图片,2.视频)
     */
    private Integer msgType;
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

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public ChatSysMsg setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public ChatSysMsg setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSysMsgType() {
        return sysMsgType;
    }

    public ChatSysMsg setSysMsgType(Integer sysMsgType) {
        this.sysMsgType = sysMsgType;
        return this;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public ChatSysMsg setMsgType(Integer msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatSysMsg setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ChatSysMsg setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ChatSysMsg setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

}