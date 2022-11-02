package com.momolela.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianFactory;
import com.caucho.hessian.io.SerializerFactory;
import com.momolela.serialize.SerializeException;
import com.momolela.serialize.support.AbstractSerializer;

import java.io.*;

public class HessianSerializer extends AbstractSerializer {

    private static final HessianFactory hf = new HessianFactory();

    @Override
    public void write(OutputStream out, Object bean) {
        Hessian2Output hessian2Output = hf.createHessian2Output(out);
        try {
            hessian2Output.writeObject(bean);
            hessian2Output.close();
        } catch (IOException e) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, e);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public byte[] write(Object bean) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.write(outputStream, bean);
        return outputStream.toByteArray();
    }

    @Override
    public Object read(InputStream input) {
        Hessian2Input hessian2Input = hf.createHessian2Input(input);
        Object bean;
        try {
            bean = hessian2Input.readObject();
            hessian2Input.close();
        } catch (IOException e) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, e);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
        return bean;
    }

    @Override
    public Object read(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Object bean = null;
        try {
            bean = this.read(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    static {
        SerializerFactory sf = new SerializerFactory();
        sf.setAllowNonSerializable(true);
        hf.setSerializerFactory(sf);
    }
}
