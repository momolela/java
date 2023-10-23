package com.momolela.deepclone.byserializable;

import java.io.*;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/8/31 09:04
 */
public class App {
    public static void main(String[] args) throws Exception {
        House house = new House("市区", 58955);
        User user = new User("张三", 18, house);

        // 序列化
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(user);

        // 反序列化
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        User cloneUser = (User) objectInputStream.readObject();

        // false
        System.out.println(user == cloneUser);
    }
}
