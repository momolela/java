package com.momolela.proxy.jdkdynamicproxy2;

public class ArithmeticCalculatorLoggingProxy {

    private ArithmeticCalculator target;
    public ArithmeticCalculatorLoggingProxy (ArithmeticCalculator target) {
        super();
        this.target = target;
    }


}
