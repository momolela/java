package com.momolela.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.concurrent.TimeUnit;

/**
 * @author sunzj
 */
@WebService
public class HelloServiceImpl implements HelloService {

    @Override
    @WebMethod
    public String returnXml(@WebParam(name = "xml") String xml) {
        try {
            TimeUnit.MILLISECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
