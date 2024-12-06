package com.momolela.strategypattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/6 17:15
 */
public class App {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("1 + 2 = " + context.executeOperation(1, 2));

        context = new Context(new OperationSubtract());
        System.out.println("1 - 2 = " + context.executeOperation(1, 2));

        context = new Context(new OperationMultiply());
        System.out.println("1 * 2 = " + context.executeOperation(1, 2));
    }
}
