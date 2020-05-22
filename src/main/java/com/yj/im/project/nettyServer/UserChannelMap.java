package com.yj.im.project.nettyServer;

import com.yj.im.project.entity.ChatFriend;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class UserChannelMap {


    //保存用户id和通道的map对象
    private static Map<String, Channel> userChannelMap;

    static {
        userChannelMap = new HashMap<>();
    }

    /**
     * 建立用户和通道直接的关联
     *
     * @param userId
     * @param channel
     */
    public static void put(Long userId, Channel channel) {
        userChannelMap.put("im" + userId, channel);
    }

    public static Channel getChannel(Long userId) {
        return userChannelMap.get("im" + userId);
    }

    /**
     * 解除用户和通道直接的关系
     *
     * @param userid
     */
    public static void remove(String userid) {
        userChannelMap.remove(userid);
    }

    public static void removeByChannelId(String channelId) {
        for (String userId : userChannelMap.keySet()) {
            if (userChannelMap.get(userId).id().asLongText().equals(channelId)) {
                BaseNettyServer.nettyLog().info("客户端连接断开,取消用户" + userId + "与通道" + channelId + "的关联");
                ChatHandler.userInfoMap.remove(userId);
                userChannelMap.remove(userId);
                break;
            }

        }
    }

    // 打印所有的用户与通道的关联数据
    public static void print() {

//        for (String s : userChannelMap.keySet()) {
//            BaseNettyServer.nettyLog().info("用户id:" + s + " 通道:" + userChannelMap.get(s).id());
//        }
    }

    /**
     * 根据用户id,获取通道
     */
    public static Channel get(String userid) {
        return userChannelMap.get(userid);
    }
}
