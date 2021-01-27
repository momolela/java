package com.momolela.net.netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.TimeoutException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
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
        String message = "POST /10.8.2.56:60023 HTTP/1.1\n" +
                "Content-Type:application/as-xml;\n" +
                "Content-Length:1271\n" +
                "Connection: Close\n" +
                "\n" +
                "<BSXml>  <MsgHeader>    <Organization>41959207-6</Organization>    <Sender>HIS</Sender>    <ServiceType>service</ServiceType>    <MsgType>ODS_03120005</MsgType>    <MsgVersion>3.3</MsgVersion>  </MsgHeader><MsgBody><as><method>phportNotifyHospitalPatients</method><seq>1</seq><params><hospitalCode>41959207-6</hospitalCode><hospitalName>创业智慧医院</hospitalName><patient><action>update</action><inpatientBrid>1572</inpatientBrid><triageNum></triageNum><triageTime>1900-01-01 00:00:00</triageTime><channelType></channelType><triageLevel></triageLevel><triageArea></triageArea><inpatientNumber>1908000203</inpatientNumber ><outpatientNumber></outpatientNumber><arriveHospitalDate>2019-09-11 16:38:27</arriveHospitalDate><wristbandNum></wristbandNum><wristbandID></wristbandID><patientComplaint></patientComplaint><name>小米</name><age>84</age><sex>男</sex><borndate>1936-10-07 00:00:00</borndate><contactPhone>13526591386</contactPhone><contactName>小米</contactName><identityType>身份证</identityType><identityNumber>342423193610071958</identityNumber><bloodOxygen></bloodOxygen><pulse></pulse><bodyTemperature></bodyTemperature><breathe></breathe><bloodPressure></bloodPressure><alertness></alertness></patient><tid></tid><pid>3259</pid></params></as></MsgBody></BSXml>";
        message = message.replaceAll("\\n", "\r\n");
        SyncHelloClientHandler ch = new SyncHelloClientHandler(message, "UTF-8");
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
//                return Channels.pipeline(new SyncHelloClientHandler("haha", "utf-8"));

//                return Channels.pipeline(new SyncHelloClientHandler("bsoft-service: bsxml格式化组件\r\nbsoft-urid: \r\nbsoft-pwd: \r\nbsoft-parameters: <BSXml> <Patient> <SourcePatientId>01041139</SourcePatientId> <SourcePatientIdType>IV</SourcePatientIdType> <AuthorOrganization>79649060-6</AuthorOrganization> <HealthRecordId/> <IdCard/> <IdCardCode/> <HealthCardId/> <MedicalInsuranceCategoryCode/> <Name>车海洋女</Name> <Sex>2</Sex> <BirthDate>20190104</BirthDate> <MaritalStatus>2</MaritalStatus> <Nationality>1</Nationality> <SystemTime>2019-01-21 20:26:16.0</SystemTime> <PrivacySign>0</PrivacySign> <Author>胡秀英</Author> <EthnicGroup>1</EthnicGroup> <OccupationCategoryCode>3</OccupationCategoryCode> <PatientPhone>13555781866</PatientPhone> <WorkUnit/> <WorkAddrPhone>13555781055</WorkAddrPhone> <Address _g=\"1\"> <AddrTypeCode>01</AddrTypeCode> <AddrTypeName>户籍住址</AddrTypeName> <Province>浙江省</Province> <City>台州市</City> <County>天台县</County> <Town>坦头西陈村</Town> <Village>-</Village> <HouseNumber>-</HouseNumber> <PostalCode>317200</PostalCode> </Address> <Card> <CardTypeCode>04</CardTypeCode> <CardNo>31800100021389929851</CardNo> </Card> <Card> <CardTypeCode>04</CardTypeCode> <CardNo>33109910015001602047</CardNo> </Card> <Card> <CardTypeCode>31</CardTypeCode> <CardNo>5001602047</CardNo> </Card> <Card> <CardTypeCode>32</CardTypeCode> <CardNo>01041139</CardNo> </Card> </Patient> <MsgHeader> <MsgVersion>3.1</MsgVersion> <LogTableName>LOG_PAT_0101_1</LogTableName> <Sender>HIS</Sender> <CreateBy>3</CreateBy> <LogTime>2019-03-21 18:36:04</LogTime> <DataId/> <EtlTaskCode>PAT_0101_1_IV</EtlTaskCode> <SourceId>1674061</SourceId> <RecordTitle>患者基本信息注册new</RecordTitle> <EffectiveTime>20190321T183604</EffectiveTime> <MsgType>PAT_0101</MsgType> <BatchId>c0ff382987a7841f</BatchId> </MsgHeader> </BSXml>\r\n", "UTF-8"));

                return Channels.pipeline(ch);
            }
        });
        // 连接到 ip port 的服务
//        clientBootstrap.connect(new InetSocketAddress("10.8.2.162", 9529));
//        clientBootstrap.connect(new InetSocketAddress("10.8.2.56", 60023));
//        clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 10001));
        ChannelFuture future = clientBootstrap.connect(new InetSocketAddress("10.8.2.56", 60023));
//        // 返回数据
        DefaultChannelFuture.setUseDeadLockChecker(false);
        future.getChannel().getCloseFuture().awaitUninterruptibly();
        clientBootstrap.releaseExternalResources();
        System.out.println(ch.getResult());
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
    protected byte[] resultByteArray = new byte[]{};
    protected boolean initiativeClose = false; // 主动断开
    protected Timer timer;

    public HelloClientHandler(String message, String charset) {
        this.message = message;
        this.charset = charset;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        this.channel = e.getChannel();
        ChannelBuffer sendBuff = ChannelBuffers.dynamicBuffer(this.message.length() + 3);
//        sendBuff.writeByte(0X0B);
        sendBuff.writeBytes(this.message.getBytes(this.charset));
//        sendBuff.writeByte(0x1C);
//        sendBuff.writeByte(0x0D);
        for (int i = 0; i < reCycle; i++) {
            try {
                if (timer != null)
                    timer.cancel();
                timer = new Timer();
                timer.schedule(new TimeOut(this), 301000);
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
//        this.result = e.getCause();
//        callEnd();

        if (e.getCause() instanceof IOException && e.getCause().getMessage().indexOf("远程主机强迫关闭了一个现有的连接") != -1) {
            System.out.println("远程主机强迫关闭了一个现有的连接");
        } else {
            this.result = e.getCause();
            callEnd();
        }
    }

    /**
     * 连接关闭
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (!initiativeClose) { // 被动断开链接
            if (!(this.result instanceof Exception)) {
                this.result = new String(resultByteArray);
                callEnd();
            }
        }
    }

    /**
     * 连接断开
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (!initiativeClose) { // 被动断开链接
            if (!(this.result instanceof Exception)) {
                this.result = new String(resultByteArray);
                callEnd();
            }
        }
    }

    /**
     * 获取最终结果
     *
     * @return
     */
    public Object getResult() {
        return this.result;
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

/**
 * 同步客户端句柄
 */
class SyncHelloClientHandler extends HelloClientHandler {
    public SyncHelloClientHandler(String message, String charset) {
        super(message, charset);
        this.message = message;
    }

    /**
     * 数据是分段接收的，要么用特殊字符串分割去处理粘包，要么服务端使用短连接
     *
     * @param ctx
     * @param e
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        try {
            ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();
            int oriLength = resultByteArray.length;
            resultByteArray = Arrays.copyOf(resultByteArray, resultByteArray.length + acceptBuff.array().length);
            for (int i = 0; i < acceptBuff.array().length; i++) {
                resultByteArray[i + oriLength] = acceptBuff.array()[i];
            }
        } catch (Exception ex) {
            this.result = ex;
            callEnd();
        }
    }
}

