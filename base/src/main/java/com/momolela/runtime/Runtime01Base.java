package com.momolela.runtime;

public class Runtime01Base {

    public static void main(String[] args) throws Exception {

        /**
         * Runtime 对象
         * 该类并没有提供构造函数，说明不可以 new 对象，那肯定里面的方法是静态的
         * 但是查到该类里面有非静态的方法，说明又需要对象才能使用，说明肯定可以通过一个静态方法去获取一个对象
         * 由此可以得出该类是单例模式设计的
         */
        Runtime runtime = Runtime.getRuntime();

        /**
         * 用于打开画图软件
         * 如下参数是 "mspaint.exe"，虽然路径找不到，但是会根据环境变量的 path 去 C:/windows/System32 里面找可运行的程序
         */
        Process process = runtime.exec("mspaint.exe");

        Thread.sleep(4000);

        process.destroy();

        /**
         * 获取 cpu 的内核数
         */
        System.out.println(runtime.availableProcessors());

    }

}
