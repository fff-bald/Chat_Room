package org.cjl.chatroom.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //此方法表示连接建立，一旦建立连接，就第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会将 channelGroup 中所有 channel 遍历，并发送消息，而不需要我们自己去遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天\n");
        //将当前的Channel加入到 ChannelGroup
        channelGroup.add(channel);
    }

    //表示 channel 处于活动状态，提示 xxx 上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "上线了~");
    }

    //表示 channel 处于不活动状态，提示 xxx 离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "离线了~");
    }

    //表示 channel 断开连接，将xx客户离开信息推送给当前在线客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() +" "+ sdf.format(new Date()) + "离开了\n");
        System.out.println("当前聊天室中的人数为 ：" + channelGroup.size());
    }

    //读取数据，并进行消息转发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //这时，遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(item -> {
            if (item != channel) {
                item.writeAndFlush("["+LocalTime.now().format(formatter)+"  "+channel.remoteAddress()+"]发送了消息：" + msg + "\n");
            } else { //把自己发送的消息发送给自己
                item.writeAndFlush("["+ LocalTime.now().format(formatter)+"  本人]发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
