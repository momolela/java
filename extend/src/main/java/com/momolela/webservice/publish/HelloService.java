package com.momolela.webservice.publish;

import javax.xml.soap.SOAPException;
import java.io.IOException;

public interface HelloService {
    String sayHello(String name);
    String helloBigData() throws IOException, SOAPException;
}
