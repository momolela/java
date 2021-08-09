package com.momolela.generic;

/**
 * 将泛型定义在方法上，相对于泛型类更加自由
 */
class GenericMethods<T> {
    /**
     * 沿用了类上定义的泛型
     *
     * @param t
     */
    public void show(T t) {
        System.out.println("show:" + t);
    }

    /**
     * 定义了方法上的泛型
     *
     * @param e
     * @param <E>
     */
    public <E> void print(E e) {
        System.out.println("print:" + e);
    }


    // 会报错，静态方法不能用类上定义的泛型，因为类上的泛型只有在 new 对象的时候才有，但是静态方法先于对象存在
    //public static void staticMethod(T t) {
    //    System.out.println("static methods:" + t);
    //}

    /**
     * 上面注释的代码会报错，静态方法不能用类上定义的泛型，因为类上的泛型只有在 new 对象的时候才有，但是静态方法先于对象存在
     * 所以可以使用泛型方法去做
     *
     * @param w
     * @param <W>
     */
    public static <W> void staticMethod(W w) {
        System.out.println("static methods:" + w);
    }
}

public class Generic04InMethods {
    public static void main(String[] args) {
        GenericMethods<String> gms = new GenericMethods<String>();
        gms.show("haha"); // 创建对象的时候，指定了泛型为 String ，如果这里传递了其他类型的参数会报错
        gms.print(new Integer(123));
        GenericMethods.staticMethod(new Integer(456));
    }
}
