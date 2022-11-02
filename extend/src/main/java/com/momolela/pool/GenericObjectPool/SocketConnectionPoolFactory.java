package com.momolela.pool.GenericObjectPool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class SocketConnectionPoolFactory {

    private GenericObjectPool<Socket> pool = null;
    private static SocketConnectionPoolFactory instance;

    private SocketConnectionPoolFactory(GenericObjectPoolConfig config, String hostPortArrStr) {
        SocketConnectionFactory socketConnectionFactory = new SocketConnectionFactory(hostPortArrStr);
        pool = new GenericObjectPool<Socket>(socketConnectionFactory, config);
    }

    public static SocketConnectionPoolFactory getInstance() {
        if (instance == null) {
            synchronized (SocketConnectionPoolFactory.class) {
                if (instance == null) {
                    try {
                        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                        Properties properties = new Properties();
                        FileInputStream fileInputStream = new FileInputStream("config.properties");
                        properties.load(fileInputStream);
                        config.setMaxIdle(Integer.parseInt((String) properties.get("maxIdle")));
                        config.setMaxWaitMillis(Integer.parseInt((String) properties.get("maxWait")));
                        config.setMinEvictableIdleTimeMillis(Integer.parseInt((String) properties.get("minEvictableIdleTimeMillis")));
                        config.setMinIdle(Integer.parseInt((String) properties.get("minIdle")));
                        config.setTestOnBorrow(Boolean.parseBoolean((String) properties.get("testOnBorrow")));
                        config.setTestOnCreate(Boolean.parseBoolean((String) properties.get("testOnCreate")));
                        config.setTestOnReturn(Boolean.parseBoolean((String) properties.get("testOnReturn")));
                        config.setTestWhileIdle(Boolean.parseBoolean((String) properties.get("testWhileIdle")));
                        config.setTimeBetweenEvictionRunsMillis(Integer.parseInt((String) properties.get("timeBetweenEvictionRunsMillis")));
                        config.setMaxTotal(Integer.parseInt((String) properties.get("maxTotal")));
                        config.setNumTestsPerEvictionRun(Integer.parseInt((String) properties.get("numTestsPerEvictionRun")));
                        config.setLifo(Boolean.parseBoolean((String) properties.get("lifo")));
                        String hostPortArrStr = (String) properties.get("server_info");
                        instance = new SocketConnectionPoolFactory(config, hostPortArrStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public Socket getConnection() throws Exception {
        return pool.borrowObject();
    }

    public void releaseConnection(Socket socket) {
        try {
            pool.returnObject(socket);
        } catch (Exception e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    e.printStackTrace();
                }
            }
        }
    }
}
