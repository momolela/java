package com.momolela.pool.GenericObjectPool;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        SocketConnectionPoolFactory socketConnectionPoolFactory = SocketConnectionPoolFactory.getInstance();
        Socket socket1 = socketConnectionPoolFactory.getConnection();
        Socket socket2 = socketConnectionPoolFactory.getConnection();
        Socket socket3 = socketConnectionPoolFactory.getConnection();
        Socket socket4 = socketConnectionPoolFactory.getConnection();
        Socket socket5 = socketConnectionPoolFactory.getConnection();
        // TimeUnit.SECONDS.sleep(2);
        // socketConnectionPoolFactory.releaseConnection(socket1); // 释放连接
        Socket socket6 = socketConnectionPoolFactory.getConnection(); // 等待 5s 后如果没有连接释放，会抛出异常，因为获取不到连接了
    }
}
