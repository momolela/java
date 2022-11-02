package com.momolela.serialize;


import com.momolela.serialize.fst.FSTSerializer;
import com.momolela.serialize.hessian.HessianSerializer;
import com.momolela.serialize.jdk.JDKSerializer;
import com.momolela.serialize.kryo.KryoSerializer;

public class SerializerFactory {
    private static final Serializer KRYO = new KryoSerializer();
    private static final Serializer FST = new FSTSerializer();
    private static final Serializer HESSIAN = new HessianSerializer();
    private static final Serializer JDK = new JDKSerializer();

    public static Serializer getSerializer(byte type) {
        switch (type) {
            case SerializerTypes.FST:
                return FST;
            case SerializerTypes.HESSIAN:
                return HESSIAN;
            case SerializerTypes.JDK:
                return JDK;
            default:
                return KRYO;
        }
    }
}