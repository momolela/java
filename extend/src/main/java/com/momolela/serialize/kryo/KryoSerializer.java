package com.momolela.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.momolela.serialize.SerializeException;
import com.momolela.serialize.support.AbstractSerializer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class KryoSerializer extends AbstractSerializer {

    @Override
    public void write(OutputStream out, Object bean) {
        // 从池子中获取 kryo
        Kryo kryo = KryoFactory.instance.getKryo();
        // 利用 kryo 的 Output 对象
        try (Output kryoOut = new Output(out)) {
            kryo.writeClassAndObject(kryoOut, bean);
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
            // 归还 kryo
            KryoFactory.instance.returnKryo(kryo);
        }
    }

    @Override
    public byte[] write(Object bean) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        this.write(bout, bean);
        return bout.toByteArray();
    }

    @Override
    public Object read(InputStream in) {
        // 从池子中获取 kryo
        Kryo kryo = KryoFactory.instance.getKryo();
        Object bean;
        // 利用 kryo 的 Input 对象
        try (Input kryoIn = new Input(in)) {
            bean = kryo.readClassAndObject(kryoIn);
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
            // 归还 kryo
            KryoFactory.instance.returnKryo(kryo);
        }
        return bean;
    }

    @Override
    public Object read(byte[] bytes) {
        Kryo kryo = KryoFactory.instance.getKryo();
        Object bean;
        try {
            Input kryoIn = new Input(bytes);
            bean = kryo.readClassAndObject(kryoIn);
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
            KryoFactory.instance.returnKryo(kryo);
        }
        return bean;
    }
}
