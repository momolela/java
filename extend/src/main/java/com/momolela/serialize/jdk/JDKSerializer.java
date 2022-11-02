package com.momolela.serialize.jdk;

import com.momolela.serialize.SerializeException;
import com.momolela.serialize.support.AbstractSerializer;

import java.io.*;

public class JDKSerializer extends AbstractSerializer {
    @Override
    public void write(OutputStream out, Object bean) {
        try (ObjectOutputStream objOut = new ObjectOutputStream(out)) {
            objOut.writeObject(bean);
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public byte[] write(Object bean) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        this.write(bout, bean);
        return bout.toByteArray();
    }

    @Override
    public Object read(InputStream input) {
        try (ObjectInputStream objIn = new ObjectInputStream(input)) {
            return objIn.readObject();
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new SerializeException(SerializeException.CODE_CLASS_NOT_FOUND, cnfe);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Object read(byte[] bytes) {
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        return this.read(bin);
    }
}
