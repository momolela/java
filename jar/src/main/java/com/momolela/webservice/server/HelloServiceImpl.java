package com.momolela.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HelloServiceImpl implements HelloService {

    @Override
    @WebMethod
    public String returnXml(String xml) {
        return xml;
    }
}
