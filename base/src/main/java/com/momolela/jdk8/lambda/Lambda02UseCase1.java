package com.momolela.jdk8.lambda;

/**
 * 接口
 */
interface ReturnOneParam {
    int method(int num);
}

/**
 * lambda 表达式引用方法
 *
 * 有时候我们不是必须要自己重写某个匿名内部类的方法，我们可以可以利用 lambda 表达式的接口快速指向一个已经被实现的方法。
 *
 * 方法归属者::方法名
 * 静态方法的归属者为类名，普通方法归属者为对象
 */
public class Lambda02UseCase1 {
    public static void main(String[] args) {
        ReturnOneParam lambda1 = a -> doubleNum(a);
        System.out.println(lambda1.method(3));

        // lambda2 引用了已经实现的 doubleNum 方法
        ReturnOneParam lambda2 = Lambda02UseCase1::doubleNum;
        System.out.println(lambda2.method(3));

        // lambda3 引用了已经实现的 addTwo 方法
        Lambda02UseCase1 lambda02UseCase1 = new Lambda02UseCase1();
        ReturnOneParam lambda3 = lambda02UseCase1::addTwo;
        System.out.println(lambda3.method(3));
    }

    /**
     * 要求
     * 1.参数数量和类型要与接口中定义的一致
     * 2.返回值类型要与接口中定义的一致
     */
    public static int doubleNum(int num) {
        return num * 2;
    }

    /**
     * 要求
     * 1.参数数量和类型要与接口中定义的一致
     * 2.返回值类型要与接口中定义的一致
     */
    public int addTwo(int num) {
        return num + 2;
    }
}
