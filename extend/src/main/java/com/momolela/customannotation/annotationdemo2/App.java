package com.momolela.customannotation.annotationdemo2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class LoginVerifyMapping {
    private static Map<String, Boolean> faceFunctionIsNeedLoginVerify = new HashMap<String, Boolean>();

    public static void add(String functionName) {
        faceFunctionIsNeedLoginVerify.put(functionName, Boolean.TRUE);
    }

    public static Boolean getFaceFunctionIsNeedLoginVerify(String functionName) {
        return faceFunctionIsNeedLoginVerify.get(functionName);
    }
}

class Test {
    private Boolean isLogin = Boolean.FALSE;

    @LoginVerify
    public void read() {
        if (LoginVerifyMapping.getFaceFunctionIsNeedLoginVerify("com.momolela.customannotation.annotationdemo2.Test.read") == Boolean.TRUE) {
            if (isLogin == Boolean.TRUE) {
                System.out.println("开始读取文件...");
            } else {
                System.out.println("请先登录...");
            }
        }
    }

    @LoginVerify
    public void write() {
        if (LoginVerifyMapping.getFaceFunctionIsNeedLoginVerify("com.momolela.customannotation.annotationdemo2.Test.read") == Boolean.TRUE) {
            if (isLogin == Boolean.TRUE) {
                System.out.println("开始写入文件...");
            } else {
                System.out.println("请先登录...");
            }
        }
    }

    public void login() {
        System.out.println("登录成功...");
        isLogin = Boolean.TRUE;
    }
}

public class App {

    public static void main(String[] args) {
        Test test = new Test();

        // 扫描测试类的方法是否有加上注解
        scanAnnotation(test);

        test.write();
        test.read();

        test.login();

        test.write();
        test.read();
    }

    private static void scanAnnotation(Test test) {
        String className = test.getClass().getName();
        Method[] methods = test.getClass().getMethods();
        for (Method method : methods) {
            LoginVerify loginVerify = method.getAnnotation(LoginVerify.class);
            if (loginVerify != null) {
                LoginVerifyMapping.add(className + "." + method.getName());
            }
        }
    }

}
