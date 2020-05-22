package com.yj.im.project.entity.mongoEntity;

import com.yj.im.project.entity.pojo.BaseMessageVo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMyFriendMsgNotRead  extends BaseMessageVo implements Serializable {

    private String id;

    private Long userId;

    private Long friendId;

    private String img;

    private String nickName;

    private Integer msgNotReadyCount;

    private String outMsg;

    private LocalDateTime outTime;

    public String getImg() {
        return img;
    }

    public ChatMyFriendMsgNotRead setImg(String img) {
        this.img = img;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public ChatMyFriendMsgNotRead setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public ChatMyFriendMsgNotRead setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
        return this;
    }

    public String getOutMsg() {
        return outMsg;
    }

    public ChatMyFriendMsgNotRead setOutMsg(String outMsg) {
        this.outMsg = outMsg;
        return this;
    }

    public String getId() {
        return id;
    }

    public ChatMyFriendMsgNotRead setId(String id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ChatMyFriendMsgNotRead setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getFriendId() {
        return friendId;
    }

    public ChatMyFriendMsgNotRead setFriendId(Long friendId) {
        this.friendId = friendId;
        return this;
    }

    public Integer getMsgNotReadyCount() {
        return msgNotReadyCount;
    }

    public ChatMyFriendMsgNotRead setMsgNotReadyCount(Integer msgNotReadyCount) {
        this.msgNotReadyCount = msgNotReadyCount;
        return this;

    }
}
