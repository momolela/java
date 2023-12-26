package com.momolela.webservice.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SendRes {

    private String rspcod;
    private String msgGroup;
    private boolean success;
}
