package com.momolela.exception;

import lombok.Data;

/**
 * 自定义异常
 * 自定义异常需要继承Exception
 * 继承了Exception或者Error或者Throwable，才具有可抛性
 */
@Data
class FushuException extends Exception {
    private int value;

    FushuException() {
    }

    /**
     * 构造函数，父类里已经定义了异常信息变量，new自定义异常时直接传入即可
     *
     * @param msg
     */
    FushuException(String msg) {
        super(msg);
    }

    /**
     * 除了传入异常信息到父类的异常信息变量，还可以传入自己定义的变量，后期可以用于自定义异常体系中的code编码
     *
     * @param msg
     * @param value
     */
    FushuException(String msg, int value) {
        super(msg);
        this.value = value;
    }
}

class Exception03CustomDemo {
    int div(int a, int b) throws FushuException {
        if (b < 0)
            throw new FushuException("出现了除数为负数的情况 /by fushu", b); // 手动通过关键字throw抛出一个自定义的异常，一个异常的抛出需要处理，还是要么try catch要么throws
        return a / b;
    }
}

public class Exception03Custom {
    public static void main(String[] args) {
        Exception03CustomDemo exception03CustomDemo = new Exception03CustomDemo();
        try {
            int div = exception03CustomDemo.div(4, -1);
            System.out.println("结果是：" + div);
        } catch (FushuException e) {
            System.out.println("自定义异常对象是：" + e.toString());
            System.out.println("自定义异常信息是：" + e.getMessage());
            System.out.println("除数出现了负数，除数是：" + e.getValue());
        }
        System.out.println("over");
    }
}
