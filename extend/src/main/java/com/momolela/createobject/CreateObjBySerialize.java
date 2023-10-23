package com.momolela.createobject;

import java.io.*;

/**
 * @author sunzj
 */
public class CreateObjBySerialize {
    public static void main(String[] args) {
        Person person = new Person("sunzj", 26);
        Person clonePerson = clone(person);
        System.out.println(clonePerson);
    }

    public static <T extends Serializable> T clone(T obj) {
        T cloneObj = null;
        try {
            // 序列化
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            // 反序列化
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cloneObj = (T) objectInputStream.readObject();
            byteArrayInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }
}
