package com.momolela.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.concurrent.TimeUnit;

@WebService
public class HelloServiceImpl implements HelloService {

    @Override
    @WebMethod
    public String returnXml(String xml) {
        try {
            TimeUnit.MILLISECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
