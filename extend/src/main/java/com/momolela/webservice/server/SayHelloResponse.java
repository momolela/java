
package com.momolela.webservice.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHelloResponse", propOrder = {
    "_return"
})
public class SayHelloResponse {

    @XmlElement(name = "return")
    protected String _return;


    public String getReturn() {
        return _return;
    }


    public void setReturn(String value) {
        this._return = value;
    }

}
