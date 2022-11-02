package com.momolela.pool.GenericObjectPool;

import com.sun.istack.internal.logging.Logger;
import groovy.util.logging.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class SocketConnectionFactory extends BasePooledObjectFactory<Socket> {

    private static final Logger logger = Logger.getLogger(SocketConnectionFactory.class);

    private AtomicLong atomicLongCount;
    private List<InetSocketAddress> inetSocketAddressList = null;

    public SocketConnectionFactory(String hostPortArrStr) {
        inetSocketAddressList = new ArrayList<>();
        String[] hostPort = hostPortArrStr.split(";");
        for (String hp : hostPort) {
            String[] hpArr = hp.split(":");
            String host = hpArr[0];
            int port = Integer.parseInt(hpArr[1]);
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);
            inetSocketAddressList.add(socketAddress);
        }
        atomicLongCount = new AtomicLong();
    }

    private InetSocketAddress getSocketAddress() {
        int index = (int) (atomicLongCount.getAndIncrement() % inetSocketAddressList.size());
        logger.info("调用服务器的地址是：" + inetSocketAddressList.get(index).getHostName());
        return inetSocketAddressList.get(index);
    }


    /**
     * 创建 socket 对象
     */
    @Override
    public Socket create() throws Exception {
        Socket socket = new Socket();
        socket.connect(getSocketAddress());
        return socket;
    }

    /**
     * 包装为可维护的对象
     */
    @Override
    public PooledObject<Socket> wrap(Socket socket) {
        return new DefaultPooledObject<>(socket);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<Socket> p) throws Exception {
        Socket socket = p.getObject();
        logger.info("销毁 socket 连接：" + socket.getInetAddress().getHostAddress());
        socket.getInputStream().close();
        socket.getOutputStream().close();
        socket.close();
    }

    /**
     * 验证对象，Pool对象可以设置借出归还时候是否需要验证对象
     */
    @Override
    public boolean validateObject(PooledObject<Socket> p) {
        Socket socket = p.getObject();
        logger.info("检验 socket 连接：" + socket.getInetAddress().getHostAddress());
        boolean state = socket.isConnected();
        if (socket.isClosed()) {
            state = false;
        }
        return state;
    }

    /**
     * 钝化归还对象，说白了就是对归还的对象清理
     * 清空输入流，避免因为上一个请求字节未读取完导致inputStream非空，对下一个产生影响
     */
    @Override
    public void passivateObject(PooledObject<Socket> p) throws Exception {
        Socket socket = p.getObject();
        logger.info("归还 socket 连接：" + socket.getInetAddress().getHostAddress());
        InputStream inputStream = socket.getInputStream();
        int available = inputStream.available();
        if (available > 0) {
            inputStream.skip(available);
        }
    }
}
