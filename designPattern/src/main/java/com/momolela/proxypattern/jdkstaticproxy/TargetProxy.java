package com.momolela.proxypattern.jdkstaticproxy;

/**
 * 针对接口创建一个目标代理类
 * 1、实现目标接口
 * 2、拥有目标接口的引用
 * 3、提供形参为目标接口的构造函数，并赋值给引用
 * 4、实现接口方法，接口方法中调用实际接口实现的方法，实际接口实现的方法前、后、异常等处都可以自己做增强
 */
public class TargetProxy implements HelloService {

    /**
     * 拥有目标接口的引用
     */
    private final HelloService helloService;

    /**
     * 提供形参为目标接口的构造函数，并赋值给引用
     */
    public TargetProxy(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public void sayHello() {
        // 增强
        System.out.println("静态代理-前置增强（通知）");

        // 调用实际接口实现的方法
        helloService.sayHello();

        // 增强
        System.out.println("静态代理-后置增强（通知）");
    }
}
