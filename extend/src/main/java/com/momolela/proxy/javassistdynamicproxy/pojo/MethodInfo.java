package com.momolela.proxy.javassistdynamicproxy.pojo;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
    private String methodName; // 方法名
    private List<ParameterInfo> parameterInfos = new ArrayList<>(); // 参数列表
    private String returnType = "void"; // 返回值类型，默认不返回
    private String modifiers; // 访问修饰符

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<ParameterInfo> getParameterInfos() {
        return parameterInfos;
    }

    public void setParameterInfos(List<ParameterInfo> parameterInfos) {
        this.parameterInfos = parameterInfos;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getModifiers() {
        return modifiers;
    }

    public void setModifiers(String modifiers) {
        this.modifiers = modifiers;
    }
}
