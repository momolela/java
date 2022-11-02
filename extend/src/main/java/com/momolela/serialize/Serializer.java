package com.momolela.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    void write(OutputStream out, Object bean);

    byte[] write(Object bean);

    void write(OutputStream out, Object bean, byte compressType);

    byte[] write(Object bean, byte compressType);

    Object read(InputStream input);

    Object read(byte[] bytes);

    Object read(InputStream input, byte compressType);

    Object read(byte[] bytes, byte compressType);
}
