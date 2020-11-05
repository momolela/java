package com.momolela.net.netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.TimeoutException;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class Netty01ClientServerBootStrap {
}

/**
 * 服务端
 */
class HelloServer {
    public static void main(String[] args) {
        // server 服务启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // 设置一个处理客户端消息和各种消息事件的类（handler）
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloServerHandler());
            }
        });
        // 绑定端口
        serverBootstrap.bind(new InetSocketAddress(9000));
    }
}

/**
 * 服务端句柄
 */
class HelloServerHandler extends SimpleChannelUpstreamHandler {

    protected String service = "";
    protected String charset = "UTF-8";
    protected ByteBuffer byteBuffer;
    protected byte[] startByte = {0x0B}; // 起始标志
    protected byte[] endByte = {0x1C, 0x0D}; // 结束标志
    private boolean write = false;

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("hello i am server");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();
        callSplitMsg(acceptBuff.array(), e.getChannel());
    }

    public void callSplitMsg(byte[] paramByte, Channel channel) {
        // 通过开始符合结束符拆分消息（解决粘包问题）
        int end = -1;
        int start = 0;
        for (int i = 0; i < paramByte.length; ) {
            if (!write) {
                // 判断开始符
                if (equalBytes(startByte, paramByte, i)) {
                    write = true;
                    i = i + startByte.length;
                    start = i;
                    end = i;
                    continue;
                }
            }

            if (write) {
                end = i;
                // 判断结束符
                if (equalBytes(endByte, paramByte, i)) {
                    write = false;
                    // 保存该消息
                    if (end >= start) {
                        System.out.println("获取到的消息：" + getByteMsg(paramByte, start, end));
                    }
                    // 清空环境
                    end = -1;
                    start = 0;
                    i = i + endByte.length;
                    continue;
                }
            }
            i++;
        }
        // 剩余消息
        if (end > start) {
            byteBuffer = getBytesSplit(start, end + 1, paramByte);
        }
    }

    private boolean equalBytes(byte[] bytarg, byte[] msg, int start) {
        if (start > msg.length || start + bytarg.length > msg.length)
            return false;
        for (byte b : bytarg) {
            if (b != msg[start])
                return false;
            start++;
        }
        return true;
    }

    private String getByteMsg(byte[] msg, int start, int end) {
        int l = end - start;
        if (byteBuffer != null) {
            l += byteBuffer.capacity();
        }
        ByteBuffer bb = ByteBuffer.allocate(l);
        if (byteBuffer != null)
            bb.put(byteBuffer.array());
        bb.put(getBytesSplit(start, end, msg).array());
        byteBuffer = null;
        return new String(bb.array(), Charset.forName(charset));
    }

    private ByteBuffer getBytesSplit(int start, int end, byte[] msg) {
        if (end >= start) {
            ByteBuffer bb = ByteBuffer.allocate(end - start);
            for (int i = start; i < end; i++) {
                bb.put(msg[i]);
            }
            return bb;
        }
        return ByteBuffer.allocate(0);
    }
}

/**
 * 客户端
 */
class HelloClient {
    public static void main(String[] args) {
        // 客户端
        ClientBootstrap clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        // 设置一个处理服务端消息和各种消息事件的类（handler）
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloClientHandler("hello i am sun", "UTF-8"));
            }
        });
        // 连接到本地端口为 9000 的服务
        clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 9000));
    }
}

/**
 * 客户端句柄
 */
class HelloClientHandler extends SimpleChannelUpstreamHandler {
    protected int reCycle = 5; // 重复次数
    protected String message; // 消息
    protected String charset; // 编码
    protected Channel channel;
    protected Object result;
    protected boolean initiativeClose = false; // 主动断开
    protected Timer timer;

    public HelloClientHandler(String message, String charset) {
        this.message = message;
        this.charset = charset;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("hello i am client");
        this.channel = e.getChannel();
        ChannelBuffer sendBuff = ChannelBuffers.dynamicBuffer(this.message.length() + 3);
        sendBuff.writeByte(0X0B);
        sendBuff.writeBytes(this.message.getBytes(this.charset));
        sendBuff.writeByte(0x1C);
        sendBuff.writeByte(0x0D);
        for (int i = 0; i < reCycle; i++) {
            try {
                if (timer != null)
                    timer.cancel();
                timer = new Timer();
                timer.schedule(new TimeOut(this), 15000);
                ChannelFuture cf = channel.write(sendBuff);
                break;
            } catch (Exception ex) {
                if (i + 1 == reCycle)
                    this.result = ex;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        this.result = (Exception) e.getCause();
        callEnd();
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (!initiativeClose) { // 被动断开链接（如果这个时候还没有返回值，则触发该异常）
            this.result = new java.io.IOException("远程主机中的软件中止了一个已建立的连接。");
            callEnd();
        }
    }

    /**
     * 主动关闭连接
     */
    protected void callEnd() {
        initiativeClose = true;
        if (timer != null)
            timer.cancel();
        if (this.channel != null)
            this.channel.close();
    }

    class TimeOut extends TimerTask {
        private HelloClientHandler ch;

        public TimeOut(HelloClientHandler ch) {
            this.ch = ch;
        }

        @Override
        public void run() {
            try {
                this.cancel();
                this.ch.result = new TimeoutException("接收数据超时");
            } finally {
                ch.callEnd();
            }
        }
    }
}

/**
 * 异步客户端句柄
 */
class AsyncHelloClientHandler extends HelloClientHandler {
    public AsyncHelloClientHandler(String message, String charset) {
        super(message, charset);
        this.message = message;
    }
}

class SyncHelloClientHandler extends HelloClientHandler {
    public SyncHelloClientHandler(String message, String charset) {
        super(message, charset);
        this.message = message;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        try {
            ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();
            this.result = acceptBuff.toString(Charset.defaultCharset()).trim();
        } catch (Exception ex) {
            this.result = ex;
        } finally {
            callEnd();
        }
    }
}

