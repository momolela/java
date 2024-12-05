package com.momolela.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/10/14 09:46
 */
public class App {
    public static void main(String[] args) {
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://10.6.189.11:3323/bship_mdr?allowMultiQueries=true&nullDatabaseMeansCurrent=true");
            dataSource.setUsername("BSHIP_MDR");
            dataSource.setPassword("bsoft123");

            DruidPooledConnection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString(3);
                System.out.println(tableName);
            }

            // 关闭连接
            tables.close();
            connection.close();
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
