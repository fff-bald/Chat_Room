# Chat_Room

Command line chat room, based on Netty.

introduce ：https://blog.csdn.net/awsl_6699/article/details/115603301

Chat_Room是一个基于Netty开发的命令行聊天室程序，使用了C/S架构，用户使用客户端向服务器发送短信，由服务器转发至所有连接中的客户端。

客户端：
1、客户端连接服务器：客户端通过服务器的ip地址和端口号连接上服务器，加入到聊天室中。
（实质上是channel和EventLoopGroup建立和连接）
2、客户端发送短信：通过SocketChannel向服务器发送数据。

服务器：
1、服务器初始化：定义EventLoopGroup，通过ServerBootstrap对服务器初始化，选择对外暴露的端口，初始化channel并绑定handler。
3、用户记录：
上线，当有客户端第一次连接服务器，该channel 处于活动状态，将channel存于数组中，channel与用户一一绑定。
下线，当channel 断开连接时，从channel数组中删除对应的channel，之后有短信传入也不会在发送到该客户端。
2、短信转发：当有客户端向服务器发送短信时，服务器遍历channel数组，通过channel向每个客户端发送这条短信。
