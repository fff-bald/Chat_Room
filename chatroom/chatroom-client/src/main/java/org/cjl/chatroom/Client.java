package org.cjl.chatroom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.cjl.chatroom.handler.DefaultChannelInitializer;
import org.cjl.chatroom.utils.WriteAndPrint;

public class Client {

    //public static String userName = "匿名";

    public static String host;

    public static int port;

    public static void main(String[] args) {
        chatClientInit();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new DefaultChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //得到channel
            Channel channel = channelFuture.channel();
            System.out.println("-----------聊天室------------");
            //客户端需要输入信息，创建一个扫描器
            while (true) {
                //通过channel发送到服务器
                String msg = WriteAndPrint.write();
                //输入exit退出聊天室
                if("exit".equals(msg))break;
                if(msg.isEmpty())continue;
                channel.writeAndFlush(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public static void chatClientInit() {
        // 这里还要对输入进行判断，
        // 例如服务器地址是否为有效地址，
        // 端口号是否为正确端口号
        // 排除不正确的信息
        WriteAndPrint.println("请输入所连接的服务器地址，不可为空：");
        host = WriteAndPrint.write().trim();
        WriteAndPrint.println("请输入所连接的服务器端口号，不可为空：");
        port = Integer.parseInt(WriteAndPrint.write().trim());
    }
}
