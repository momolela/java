package com.momolela.serialize.kryo.support;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.momolela.serialize.SerializeException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class DefaultDocumentKryoSerializer extends Serializer<Document> {

    @Override
    public void write(Kryo kryo, Output output, Document doc) {
        if (doc == null) {
            output.writeString(null);
        } else {
            output.writeString(doc.asXML());
        }

    }

    @Override
    public Document read(Kryo kryo, Input input, Class<Document> type) {
        String xml = input.readString();
        if (xml == null) {
            return null;
        } else {
            try {
                return DocumentHelper.parseText(xml);
            } catch (DocumentException de) {
                throw new SerializeException(de);
            }
        }
    }
}
