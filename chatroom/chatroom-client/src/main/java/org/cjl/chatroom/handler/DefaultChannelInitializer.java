package org.cjl.chatroom.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().
                addLast("decoder", new StringDecoder()).
                addLast("encoder", new StringEncoder()).
                addLast(new ChatClientHandler());
    }
}
