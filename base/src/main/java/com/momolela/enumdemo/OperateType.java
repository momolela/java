package com.momolela.enumdemo;

/**
 * @author sunzj
 */
public enum OperateType {

    S("S", "发布告警"),
    SS("SS", "发布告警后暂停服务"),
    SC("SC", "发布告警后关闭服务");


    private final String code;
    private final String name;

    OperateType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

class App {
    public static void main(String[] args) {
        System.out.println(OperateType.valueOf("SS").getName());
    }
}