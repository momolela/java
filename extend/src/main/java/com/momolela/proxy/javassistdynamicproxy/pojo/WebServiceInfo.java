package com.momolela.proxy.javassistdynamicproxy.pojo;

public class WebServiceInfo {

    private String namespace; // 发布的 wsdl 命名空间
    private String serviceName; // 发布的 wsdl 中 service 节点的name
    private String portName; // 发布的 wsdl 中 service 节点下 port name

    public WebServiceInfo(String namespace, String serviceName, String portName) {
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.portName = portName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
