package com.momolela.classloader.customclassloadertoencode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DeClassLoader extends ClassLoader {
    private String mLibPath;

    public DeClassLoader(String path) {
        mLibPath = path;
    }

    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(mLibPath, fileName);
        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            byte b = 0;
            try {
                while ((len = is.read()) != -1) {
                    b = (byte) (len ^ 2); // 将数据异或一个数字2进行解密
                    bos.write(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bos.toByteArray();
            is.close();
            bos.close();
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".classen";
        } else {
            return name.substring(index + 1) + ".classen";
        }
    }
}
