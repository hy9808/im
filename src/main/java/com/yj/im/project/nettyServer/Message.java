package com.yj.im.project.nettyServer;

import com.yj.im.project.entity.ChatRecord;

import java.io.Serializable;

public class Message implements Serializable {

    /**
     * 服务端接受参数
     * type          消息类型     0.连接  1.发送消息  2.接收消息  3.客户端保持心跳  4.群聊  500.异常
     * chatRecord {
     *      * @param {Object} msgType 消息类型  0.文字 1.图片,2.视频,
     *      * @param {Object} userId 发送的id
     *      * @param {Object} recipientId 接收者id
     *      * @param {Object} msg 消息
     *      * @param {Object} msgid 对应的消息id  (读取是需要加入)
     * }
     */

    /**
     * 客户端
     * type   消息类型     0.连接  1.客户端向服务器发送消息(发消息)  2.服务端向客户端发送消息(接收消息)   4.群聊 3.客户端保持心跳 500.异常
     * 备注：即: 客户端发送的统一为 1
     * 服务端发送的统一为 2
     * 前后端心跳包发送统一为 3
     * <p>
     * chatRecord {
     * * @param {Object} msgType 消息类型  0.文字 1.图片,2.视频,
     * * @param {Object} userId 发送的id
     * * @param {Object} recipientId 接收者id
     * * @param {Object} msg 消息(如果msgType为1,2此字段为文件路径)
     * * @param {Object} msgid 对应的消息id  (读取是需要加入)
     * }
     */

    private Integer type;//消息类型

    private ChatRecord chatRecord;//聊天消息

    private Object ext; //附加消息

    public Integer getType() {
        return type;
    }

    public Message setType(Integer type) {
        this.type = type;
        return this;
    }

    public ChatRecord getChatRecord() {
        return chatRecord;
    }

    public Message setChatRecord(ChatRecord chatRecord) {
        this.chatRecord = chatRecord;
        return this;

    }

    public Object getExt() {
        return ext;
    }

    public Message setExt(Object ext) {
        this.ext = ext;
        return this;

    }
}
