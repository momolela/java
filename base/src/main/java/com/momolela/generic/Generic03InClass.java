package com.momolela.generic;

/**
 * 以前的做法，统一定义成 Object，让所有的对象都可被接收
 */
class UtilsBefore {
    private Object tool;

    public Object getTool() {
        return tool;
    }

    public void setTool(Object tool) {
        this.tool = tool;
    }
}

/**
 * 现在的做法，定义成泛型类，要用的类型通过创建对象的时候指定，然后类中直接运用该类型
 * 1、泛型类，如果没有指定具体的数据类型，此时，操作类型是 Object
 * 2、泛型的类型参数只能是类类型，不能是基本数据类型，比如 int
 * 3、泛型类型在使用逻辑上可以看成是多个不同的类型，【但实际上都是相同类型】
 *
 * @param <T>
 */
class UtilsNow<T> {
    private T tool;

    public T getTool() {
        return tool;
    }

    public void setTool(T tool) {
        this.tool = tool;
    }
}

/**
 * 子类要用 UtilsNowChild1<K, V>，子类的泛型中必须包含父类的泛型，例如使用 UtilsNow<K>
 * 因为【子类也是泛型类，子类和父类的泛型类型必须要有一致的，因为子类创建的时候，会把泛型传递给父类，不然父类没有办法生成】
 * 创建子类的时候，也不知道要传入什么具体的类型，可以再定义泛型子类
 *
 * @param <K>
 * @param <V>
 */
class UtilsNowChild1<K, V> extends UtilsNow<K> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

/**
 * 子类要用 UtilsNowChild2，父类泛型必须指定类型 UtilsNow<String>
 * 因为【子类如果不是泛型类，父类需要明确类型，因为子类创建的时候，没有传递类型，需要父类自己明确，不然父类没有办法生成】
 * 创建子类的时候，知道要传入的类型可以直接指定
 */
class UtilsNowChild2 extends UtilsNow<String> {
    private String child;

    public String childFn() {
        return child;
    }
}

/**
 * 现在做法的扩展，类上使用泛型的限定，但是不能用 ?
 *
 * @param <T>
 */
class UtilsNowExtends<T extends String> {
    private T tool;

    public T getTool() {
        return tool;
    }

    public void setTool(T tool) {
        this.tool = tool;
    }
}

public class Generic03InClass {
    public static void main(String[] args) {
        UtilsNow<String> utilsNow = new UtilsNow<String>();
        String tool = utilsNow.getTool();

        UtilsNowExtends<String> utilsNowExtends = new UtilsNowExtends<String>();
        String toolExtends = utilsNowExtends.getTool();
    }
}
