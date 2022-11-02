package com.momolela.serialize.fst.support;

import java.io.IOException;

import com.momolela.serialize.SerializeException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.nustaq.serialization.FSTClazzInfo.FSTFieldInfo;

public class DefaultDocumentFSTSerializer extends FSTBasicObjectSerializer {
    public DefaultDocumentFSTSerializer() {
    }

    @Override
    public void writeObject(FSTObjectOutput out, Object bean, FSTClazzInfo clzInfo, FSTFieldInfo referencedBy, int streamPosition) throws IOException {
        if (bean == null) {
            out.writeObject(null);
        } else {
            Document doc = (Document) bean;
            out.writeStringUTF(doc.asXML());
        }

    }

    @Override
    public Object instantiate(Class objectClass, FSTObjectInput input, FSTClazzInfo serializationInfo, FSTFieldInfo referencee, int streamPosition) {
        try {
            String text = input.readStringUTF();
            if (text == null) {
                return null;
            } else {
                try {
                    return DocumentHelper.parseText(text);
                } catch (DocumentException de) {
                    throw new SerializeException(de);
                }
            }
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        }
    }
}
