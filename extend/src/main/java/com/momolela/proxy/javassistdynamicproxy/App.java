package com.momolela.proxy.javassistdynamicproxy;

import com.momolela.proxy.javassistdynamicproxy.pojo.FieldsInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.MethodInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.ParameterInfo;
import com.momolela.proxy.javassistdynamicproxy.pojo.WebServiceInfo;

import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // 要发布的 WS 地址
        String url = "http://10.1.0.179:8888/customWS";

        // 初始化 WS 信息
        WebServiceInfo webServiceInfo = new WebServiceInfo("namespace", "serviceName", "portName");

        // 初始化方法信息
        List<MethodInfo> methodInfoList = new ArrayList<>();
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setMethodName("printInput");
        methodInfo.setReturnType("void");
        methodInfo.setModifiers("public");
        List<ParameterInfo> parameterInfos = new ArrayList<>();
        ParameterInfo parameterInfo1 = new ParameterInfo();
        parameterInfo1.setParamName("input1");
        parameterInfo1.setClassName("java.lang.String");
        parameterInfos.add(parameterInfo1);
        ParameterInfo parameterInfo2 = new ParameterInfo();
        parameterInfo2.setParamName("input2");
        parameterInfo2.setClassName("java.lang.String");
        parameterInfos.add(parameterInfo2);
        methodInfo.setParameterInfos(parameterInfos);
        methodInfoList.add(methodInfo);

        // 初始化字段信息
        List<FieldsInfo> fieldsInfoList = new ArrayList<>();

        // 初始化工厂类
        CustomWSProxyFactory customWSProxyFactory = new CustomWSProxyFactory(webServiceInfo, methodInfoList, fieldsInfoList);
        // 获取代理类
        Object objProxy = customWSProxyFactory.getProxy();
        // 发布 WS
        Endpoint.publish(url, objProxy);
        System.out.println("自定义的 WS 服务启动了");
    }
}
