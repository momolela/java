package com.momolela.exception;

import lombok.Data;

/**
 * Exception有一个特殊的子类异常RuntimeException，运行时异常
 * 在函数内部抛出的异常是RuntimeException或者其子类异常，函数上可以不用声明，编译ok，因为不用调用者try catch或者throws去处理。
 * 在函数上声明了RuntimeException或者其子类异常，调用者可以不用处理，编译ok，因为不用调用者try catch或者throws去处理。
 * 因为出现了运行时异常，程序会立即停止，需要修改程序，不需要try catch或者throws去处理。
 */
@Data
class FushuRuntimeException extends RuntimeException {
    private String code = "mo500";
    private String msg = "除数为负数";

    FushuRuntimeException() {
    }

    FushuRuntimeException(String msg) {
        super(msg);
    }

    FushuRuntimeException(Exception e) {
        super(e);
    }

    FushuRuntimeException(String msg, Exception e) {
        super(msg, e);
    }

    FushuRuntimeException(String code, String msg, Exception e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
}

class ExceptionRuntimeDemo {
    int div(int a, int b) {
        if (b < 0)
            throw new FushuRuntimeException("出现了除数为负数的情况 /by fushu"); // 手动通过关键字throw抛出一个自定义的运行时异常，因为是运行时异常，方法上不用声明，也不需要调用者处理
        return a / b;
    }
}

public class ExceptionRuntime {
    public static void main(String[] args) {
        ExceptionRuntimeDemo exceptionRuntimeDemo = new ExceptionRuntimeDemo();
        try {
            int div = exceptionRuntimeDemo.div(4, -1);
            System.out.println("结果是：" + div);
        } catch (Exception e) {
            System.out.println("自定义异常对象是：" + e.toString());
            System.out.println("自定义异常信息是：" + e.getMessage());
        }
        System.out.println("over");
    }
}
