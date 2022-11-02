package com.momolela.serialize.support;

import com.momolela.serialize.SerializeException;
import com.momolela.serialize.Serializer;
import com.momolela.serialize.compress.CompressStreamFactory;

import java.io.*;

public abstract class AbstractSerializer implements Serializer {
    @Override
    public void write(OutputStream out, Object bean, byte compressType) {
        try (OutputStream outputStream = CompressStreamFactory.buildOutputStream(out, compressType)) {
            this.write(outputStream, bean);
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public byte[] write(Object bean, byte compressType) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        this.write(bout, bean, compressType);
        return bout.toByteArray();
    }

    @Override
    public Object read(InputStream input, byte compressType) {
        try (InputStream inputStream = CompressStreamFactory.buildInputStream(input, compressType)) {
            return this.read(inputStream);
        } catch (IOException e) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, e);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Object read(byte[] bytes, byte compressType) {
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        return this.read(bin, compressType);
    }
}
