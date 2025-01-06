package com.momolela.proxy.jdkdynamicproxy2;

/**
 * @author sunzj
 */
public class App {
    public static void main(String[] args) {
        ArithmeticCalculatorImpl arithmeticCalculator = new ArithmeticCalculatorImpl();
        ArithmeticCalculatorLoggingProxy arithmeticCalculatorLoggingProxy = new ArithmeticCalculatorLoggingProxy(arithmeticCalculator);
        ArithmeticCalculator loggingProxy = arithmeticCalculatorLoggingProxy.getLoggingProxy();
        int addResult = loggingProxy.add(1, 2);
        System.out.println("add 方法的返回：" + addResult);
        int multiplyResult = loggingProxy.multiply(1, 2);
        System.out.println("multiply 方法的返回：" + multiplyResult);
    }
}
