package com.yj.im.project.nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HearBeatHandler extends ChannelInboundHandlerAdapter {

    //触发用户事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {//读空闲
                //检测到读空闲不做任何的操作
                BaseNettyServer.nettyLog().warn("读空闲事件触发......");
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {//写空闲
                //检测到写空闲不做任何的操作
                BaseNettyServer.nettyLog().warn("写空闲事件触发...");
            } else if (idleStateEvent.state() == IdleState.ALL_IDLE) {//读写空闲
                BaseNettyServer.nettyLog().info("----------------------------------------------------------------------------");
                BaseNettyServer.nettyLog().info("--------------------------读写空闲事件触发----------------------------------");
                BaseNettyServer.nettyLog().warn("--------------------------即将关闭通道资源----------------------------------");
                ctx.channel().close();
                BaseNettyServer.nettyLog().warn("------------------------------资源关闭---------------------------------------");
            }
        }
    }
}
