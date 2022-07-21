package com.momolela.net.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

/**
 * 利用 Netty 开启一个 websocket 的服务
 * ws://localhost:8899/ws
 */
public class WebSocketServer {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // websocket 协议本身是基于 http 协议的，所以这边也要使用http解编码器
                        pipeline.addLast(new HttpServerCodec());
                        // 以块的方式来写的处理器
                        pipeline.addLast(new ChunkedWriteHandler());
                        // netty 是基于分段请求的，HttpObjectAggregator 的作用是将请求分段再聚合,参数是聚合字节的最大长度
                        pipeline.addLast(new HttpObjectAggregator(8192));


                        // 参数指的是 context_path
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                        pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
                                System.out.println("收到消息：" + msg.text());
                                ctx.channel().writeAndFlush(new TextWebSocketFrame("服务时间：" + LocalDateTime.now() + "\n后端收到的消息是：" + msg.text()));
                            }
                        });
                    }
                })
                .bind(new InetSocketAddress(8899)).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
