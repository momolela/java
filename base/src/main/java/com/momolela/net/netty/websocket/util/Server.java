package com.momolela.net.netty.websocket.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.TimeUnit;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/5/30 23:06
 */
public class Server {
    public static void main(String[] args) {
        DefaultChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        MyWebSocketHandler handler = new MyWebSocketHandler(group);

        // 接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理 I/O 操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 自定义的初始化器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 添加 HTTP 编码器和解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 聚合 HTTP 消息
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 添加 WebSocket 支持
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                            // 添加你的 WebSocket 帧处理器
                            group.add(ch);
                            pipeline.addLast(handler);
                        }
                    });

            // 绑定端口并开始接收连接
            ChannelFuture f = b.bind(8899).sync();

            // 给前端发送信息
            new Thread(() -> {
                try {
                    for (int i = 0; i < 10000; i++) {
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("你好客户端");
                        handler.sendToAll("你好客户端" + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // 等待服务器套接字关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
