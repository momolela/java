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
        Kryo kryo = KryoFactory.instance.getKryo();
        try (Output kryoOut = new Output(out)) {
            kryo.writeClassAndObject(kryoOut, bean);
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
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
        Kryo kryo = KryoFactory.instance.getKryo();
        Object bean;
        try (Input kryoIn = new Input(in)) {
            bean = kryo.readClassAndObject(kryoIn);
        } catch (Exception e) {
            throw new SerializeException(e);
        } finally {
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
