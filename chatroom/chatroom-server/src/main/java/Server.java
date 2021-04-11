import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.cjl.chatroom.handler.DefaultChannelInitializer;
import org.cjl.chatroom.utils.MsgUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {

    private static int port;

    public static void main(String[] args) throws InterruptedException {
        init();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup =  new NioEventLoopGroup();
        MsgUtils.write();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//服务端给BossGroup设置SO_BACKLOG任务队列大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//服务端给WorkerGroup设置连接SO_KEEPALIVE保持连接状态
                    .childHandler(new DefaultChannelInitializer());
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("netty服务器启动,服务器的ip地址是"+ip.getHostAddress()+",端口号是"+port+"。");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        return;
    }

    public static void init(){
        System.out.println("请确认服务器对外暴露端口：");
        port = Integer.parseInt(MsgUtils.write());
        return;
    }

}
