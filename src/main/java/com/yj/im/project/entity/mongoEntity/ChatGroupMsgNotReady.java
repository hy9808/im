package com.yj.im.project.entity.mongoEntity;

import com.yj.im.project.entity.pojo.BaseMessageVo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatGroupMsgNotReady extends BaseMessageVo implements Serializable {

    private String id;

    private Long userId;

    private Long groupId;

    private Long friendId;//如果是好友消息的话

    private String img;

    private String nickName;

    private Integer msgNotReadyCount;

    private String outMsg;

    private LocalDateTime outTime;

    public ChatGroupMsgNotReady() {
        this.setSubMsgType(3);
    }

    public ChatGroupMsgNotReady(ChatMyFriendMsgNotRead c) {
        this.id = c.getId();
        this.userId = c.getUserId();
        this.friendId = c.getFriendId();
        this.nickName = c.getNickName();
        this.msgNotReadyCount = c.getMsgNotReadyCount();
        this.outMsg = c.getOutMsg();
        this.outTime = c.getOutTime();
        this.img = c.getImg();
        this.setSubMsgType(c.getSubMsgType());
    }

    public String getImg() {
        return img;
    }

    public ChatGroupMsgNotReady setImg(String img) {
        this.img = img;
        return this;
    }

    public Long getFriendId() {
        return friendId;
    }

    public ChatGroupMsgNotReady setFriendId(Long friendId) {
        this.friendId = friendId;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public ChatGroupMsgNotReady setNickName(String nickName) {
        this.nickName = nickName;
        return this;

    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public ChatGroupMsgNotReady setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
        return this;
    }

    public String getOutMsg() {
        return outMsg;
    }

    public ChatGroupMsgNotReady setOutMsg(String outMsg) {
        this.outMsg = outMsg;
        return this;
    }

    public String getId() {
        return id;
    }

    public ChatGroupMsgNotReady setId(String id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatGroupMsgNotReady setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public ChatGroupMsgNotReady setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Integer getMsgNotReadyCount() {
        return msgNotReadyCount;
    }

    public ChatGroupMsgNotReady setMsgNotReadyCount(Integer msgNotReadyCount) {
        this.msgNotReadyCount = msgNotReadyCount;
        return this;

    }
}
