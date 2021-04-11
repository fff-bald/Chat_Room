package org.cjl.chatroom.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.cjl.chatroom.utils.MegStorage;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        MegStorage.add(msg);
        MegStorage.show();
        return;
    }
}
