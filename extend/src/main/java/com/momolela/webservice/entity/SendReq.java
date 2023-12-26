package com.momolela.webservice.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SendReq {
    private String ecName;
    private String apId;
    private String secretKey;
    private String mobiles;
    private String content;
    private String sign;
    private String addSerial;
    private String mac;
}
