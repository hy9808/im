package com.yj.im.project.entity.pojo;


import java.io.Serializable;

public class BaseMessageVo  implements Serializable {


    private Integer subMsgType; //哪个消息 0.系统  1.订单 2好友   3群


    public Integer getSubMsgType() {
        return subMsgType;
    }

    public BaseMessageVo setSubMsgType(Integer subMsgType) {
        this.subMsgType = subMsgType;
        return this;
    }
}
