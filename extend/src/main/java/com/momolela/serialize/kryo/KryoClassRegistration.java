package com.momolela.serialize.kryo;

import com.esotericsoftware.kryo.Serializer;

public class KryoClassRegistration {
    private final int id;
    private final Class<?> clz;
    private final Serializer<?> serializer;

    public KryoClassRegistration(int id, Class<?> clz) {
        this(id, clz, null);
    }

    public KryoClassRegistration(int id, Class<?> clz, Serializer<?> serializer) {
        this.id = id;
        this.clz = clz;
        this.serializer = serializer;
    }

    public int getId() {
        return this.id;
    }

    public Class<?> getClz() {
        return this.clz;
    }

    public Serializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            KryoClassRegistration that = (KryoClassRegistration)o;
            return this.id == that.id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}