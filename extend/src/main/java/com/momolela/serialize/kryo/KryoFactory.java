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
 * @author sunzj
 */
public class KryoFactory {

    public static final KryoFactory INSTANCE = new KryoFactory();

    private static final Set<KryoClassRegistration> KRYO_CLASS_REGISTRATIONS = Sets.newConcurrentHashSet();

    private final GenericObjectPool<Kryo> kryoPool = new GenericObjectPool(new KryoObjectFactory());

    public static void addKryoClassRegistration(KryoClassRegistration r) {
        if (!KRYO_CLASS_REGISTRATIONS.add(r)) {
            throw new IllegalStateException(String.format("KryoClassRegistration clz[%s] failed as duplicated id[%d]", r.getClz(), r.getId()));
        } else {
            if (INSTANCE.getPoolCount() > 0L) {
                INSTANCE.clear();
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
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(10, HashMap.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(11, ConcurrentHashMap.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(12, ArrayList.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(13, Date.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(14, Time.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(15, Timestamp.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(16, BigDecimal.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(17, Object[].class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(18, Class.class));
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(19, Object.class));
        // 序列化 xml 文件的 Registration
        KRYO_CLASS_REGISTRATIONS.add(new KryoClassRegistration(20, DefaultDocument.class, new DefaultDocumentKryoSerializer()));
    }

    private static final class KryoObjectFactory extends BasePooledObjectFactory<Kryo> {

        @Override
        public Kryo create() {

            Kryo kryo = new Kryo();

            // 设置 ClassLoader
            ClassLoader loader = KryoObjectFactory.class.getClassLoader();
            if (loader != null) {
                kryo.setClassLoader(loader);
            }
            // 设置是否注册全限定名
            kryo.setRegistrationRequired(false);
            // 设置初始化策略，如果没有默认无参构造器，那么就需要设置此项,使用此策略构造一个无参构造器
            kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            // 当有修饰符 transient 修饰的变量不能被序列化
            kryo.getFieldSerializerConfig().setCopyTransient(false);
            // 对 A 对象序列化时，默认情况下 kryo 会在每个成员对象第一次序列化时写入一个数字
            // 该数字逻辑上就代表了对该成员对象的引用，如果后续有引用指向该成员对象
            // 则直接序列化之前存入的数字即可，而不需要再次序列化对象本身
            // 这种默认策略对于成员存在互相引用的情况较有利，否则就会造成空间浪费
            // 因为每次序列化一个成员对象，都多序列化一个数字
            // 通常情况下可以将该策略关闭，kryo.setReferences(false);
            // kryo.setReferences(false);

            for (KryoClassRegistration r : KryoFactory.KRYO_CLASS_REGISTRATIONS) {
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
