package com.momolela.proxy.jdkdynamicproxy2;

/**
 * @author sunzj
 */
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

}
