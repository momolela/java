package com.momolela.webservice.entity;

import java.util.HashMap;
import java.util.Map;

public class JSONResponseBean {
    private int code = 200;

    private String msg;

    private Object body;

    private Map<String, Object> properties;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return this.body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public Object getProperty(String nm) {
        if (this.properties == null || this.properties.isEmpty()) {
            return null;
        }
        return this.properties.get(nm);
    }

    public void setProperty(String name, Object val) {
        if (this.properties == null)
            this.properties = new HashMap<>();
        this.properties.put(name, val);
    }
}