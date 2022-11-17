package com.momolela.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.Serializer;
import com.google.common.collect.Sets;
import com.momolela.serialize.SerializeException;
import com.momolela.serialize.kryo.support.DefaultDocumentKryoSerializer;
import de.javakaffee.kryoserializers.*;
import de.javakaffee.kryoserializers.guava.ImmutableListSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMapSerializer;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.dom4j.tree.DefaultDocument;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.omg.CORBA.Object;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * kryo 的工厂类
 * 提供池化对象 kryoPool，因为 kryo 的创建很消耗资源
 */
public class KryoFactory {
    public static final KryoFactory instance = new KryoFactory();
    private static final Set<KryoClassRegistration> kryoClassRegistrations = Sets.newConcurrentHashSet();
    private final GenericObjectPool<Kryo> kryoPool = new GenericObjectPool(new KryoObjectFactory());

    public static void addKryoClassRegistration(KryoClassRegistration r) {
        if (!kryoClassRegistrations.add(r)) {
            throw new IllegalStateException(String.format("KryoClassRegistration clz[%s] failed as duplicated id[%d]", r.getClz(), r.getId()));
        } else {
            if (instance.getPoolCount() > 0L) {
                instance.clear();
            }
        }
    }

    public Kryo getKryo() {
        try {
            return this.kryoPool.borrowObject();
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    public void returnKryo(Kryo kryo) {
        this.kryoPool.returnObject(kryo);
    }

    public long getPoolCount() {
        return this.kryoPool.getCreatedCount();
    }

    public void clear() {
        this.kryoPool.clear();
    }

    static {
        kryoClassRegistrations.add(new KryoClassRegistration(10, HashMap.class));
        kryoClassRegistrations.add(new KryoClassRegistration(11, ConcurrentHashMap.class));
        kryoClassRegistrations.add(new KryoClassRegistration(12, ArrayList.class));
        kryoClassRegistrations.add(new KryoClassRegistration(13, Date.class));
        kryoClassRegistrations.add(new KryoClassRegistration(14, Time.class));
        kryoClassRegistrations.add(new KryoClassRegistration(15, Timestamp.class));
        kryoClassRegistrations.add(new KryoClassRegistration(16, BigDecimal.class));
        kryoClassRegistrations.add(new KryoClassRegistration(17, Object[].class));
        kryoClassRegistrations.add(new KryoClassRegistration(18, Class.class));
        kryoClassRegistrations.add(new KryoClassRegistration(19, Object.class));
        // 序列化 xml 文件的 Registration
        kryoClassRegistrations.add(new KryoClassRegistration(20, DefaultDocument.class, new DefaultDocumentKryoSerializer()));
    }

    private static final class KryoObjectFactory extends BasePooledObjectFactory<Kryo> {

        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            ClassLoader loader = KryoObjectFactory.class.getClassLoader();
            if (loader != null) {
                kryo.setClassLoader(loader);
            }

            kryo.setRegistrationRequired(false);
            kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            // 当有修饰符 transient 修饰的变量不能被序列化
            kryo.getFieldSerializerConfig().setCopyTransient(false);

            for (KryoClassRegistration r : KryoFactory.kryoClassRegistrations) {
                Serializer<?> ser = r.getSerializer();
                if (ser != null) {
                    kryo.register(r.getClz(), ser, r.getId());
                } else {
                    kryo.register(r.getClz(), r.getId());
                }
            }

            kryo.register(Collections.singletonList("").getClass(), new ArraysAsListSerializer());
            kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
            kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
            kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
            kryo.register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer());
            kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
            kryo.register(Collections.singletonMap("", "").getClass(), new CollectionsSingletonMapSerializer());
            kryo.register(URI.class, new URISerializer());
            kryo.register(Pattern.class, new RegexSerializer());

            UnmodifiableCollectionsSerializer.registerSerializers(kryo);
            SynchronizedCollectionsSerializer.registerSerializers(kryo);
            ImmutableListSerializer.registerSerializers(kryo);
            ImmutableMapSerializer.registerSerializers(kryo);

            SubListSerializers.addDefaultSerializers(kryo);

            return kryo;
        }

        @Override
        public PooledObject<Kryo> wrap(Kryo kryo) {
            // 包装为可维护的对象
            return new DefaultPooledObject(kryo);
        }
    }
}
