package com.momolela.serialize.fst;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.momolela.serialize.SerializeException;
import com.momolela.serialize.fst.support.DefaultDocumentFSTSerializer;
import com.momolela.serialize.support.AbstractSerializer;
import org.dom4j.tree.DefaultDocument;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public class FSTSerializer extends AbstractSerializer {
    private static final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public static void addFSTSerializer(Class<?> clz, FSTBasicObjectSerializer ser, boolean forSubclasses) {
        conf.registerSerializer(clz, ser, forSubclasses);
    }

    public static void registerCls(Class<?>... clz) {
        conf.registerClass(clz);
    }

    @Override
    public void write(OutputStream out, Object bean) {
        try {
            FSTObjectOutput fout = conf.getObjectOutput(out);
            fout.writeObject(bean);
            fout.flush();
            out.close();
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public byte[] write(Object bean) {
        try {
            return conf.asByteArray(bean);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Object read(InputStream in) {
        try {
            FSTObjectInput fin = conf.getObjectInput(in);
            Object bean = fin.readObject();
            in.close();
            return bean;
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        } catch (ClassNotFoundException cnf) {
            throw new SerializeException(SerializeException.CODE_CLASS_NOT_FOUND, cnf);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Object read(byte[] bytes) {
        try {
            return conf.asObject(bytes);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    static {
        conf.setForceSerializable(true);
        conf.registerClass(DefaultDocument.class);
        conf.registerSerializer(DefaultDocument.class, new DefaultDocumentFSTSerializer(), false);
    }
}
