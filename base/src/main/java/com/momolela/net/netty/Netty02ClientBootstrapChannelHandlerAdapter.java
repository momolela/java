package com.momolela.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

public class Netty02ClientBootstrapChannelHandlerAdapter {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1024 * 1024))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            String message = "POST /10.8.2.56:60023 HTTP/1.1\nContent-Type:application/as-xml;\nContent-Length: 562\nConnection: Close\n\n<BSXml><MsgHeader><Organization>1</Organization><Sender>ECIS</Sender><ServiceType>service</ServiceType><MsgType>ODS_07010002</MsgType><MsgVersion>3.0</MsgVersion></MsgHeader><MsgBody><as><seq>5289</seq><method>phportSetWristbands</method><params><hospitalCode>cy01</hospitalCode><hospitalName>创业智慧医院 </hospitalName><wristbandNum>0101</wristbandNum><wristbandID>42</wristbandID><tid>691</tid><pid>4859</pid><inpatientBrid>3184</inpatientBrid><triageNum>1223</triageNum><channelType>cs</channelType><operation>bind</operation></params></as></MsgBody></BSXml>";
                            message = message.replaceAll("\\n", "\r\n");
                            pipeline.addLast(new Netty02ClientHandler(message, false));
                        }
                    });
            ChannelFuture future = bootstrap.connect("10.8.2.56", 60023);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}

class Netty02ClientHandler extends ChannelHandlerAdapter {

    private ByteBuf byteMsg;

    private boolean flag;

    public Netty02ClientHandler(String xml, boolean flag) {
        this.flag = flag;
        byte[] req = null;
        try {
            req = xml.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byteMsg = Unpooled.buffer(req.length);
        byteMsg.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(byteMsg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
            flag = true;
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        if (flag) {
            ctx.close();
        } else {
            ctx.read();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 释放资源
        ctx.close();
    }
}


