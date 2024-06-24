package com.momolela.net.netty.websocket.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/5/30 23:09
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    final ChannelGroup group;

    public MyWebSocketHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 处理接收到的 WebSocket 文本消息
        System.out.println("Received WebSocket message: " + msg.text());

        // 发送响应消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("Hello, client!"));
    }

    /**
     * 其他方法，如连接打开、关闭等事件的处理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 当处理器被添加到 ChannelPipeline 中时调用
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当处理器从 ChannelPipeline 中移除时调用
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当发生异常时调用
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 主动发送消息给客户端
     *
     * @param message
     */
    public void sendToAll(String message) {
        group.writeAndFlush(new TextWebSocketFrame(message));
    }
}
