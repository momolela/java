package com.momolela.pool.GenericObjectPool;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        SocketConnectionPoolFactory socketConnectionPoolFactory = SocketConnectionPoolFactory.getInstance();
        Socket socket = socketConnectionPoolFactory.getConnection();
        TimeUnit.SECONDS.sleep(5);
        socketConnectionPoolFactory.releaseConnection(socket);
    }
}
