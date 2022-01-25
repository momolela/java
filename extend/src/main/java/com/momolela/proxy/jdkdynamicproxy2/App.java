package com.momolela.proxy.jdkdynamicproxy2;

public class App {
    public static void main(String[] args) {
        ArithmeticCalculatorImpl arithmeticCalculator = new ArithmeticCalculatorImpl();
        ArithmeticCalculatorLoggingProxy arithmeticCalculatorLoggingProxy = new ArithmeticCalculatorLoggingProxy(arithmeticCalculator);
        int result = arithmeticCalculatorLoggingProxy.getLoggingProxy().add(1, 2);
        System.out.println("main 方法的返回：" + result);
    }
}
