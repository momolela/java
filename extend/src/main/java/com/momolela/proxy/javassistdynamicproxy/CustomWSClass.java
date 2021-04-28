package com.momolela.proxy.javassistdynamicproxy;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class CustomWSClass {
    @WebMethod(operationName = "customInvoke")
    public String customizeInvoke(@WebParam(name = "parameter") String parameter) {
        return "custom ws class";
    }
}
