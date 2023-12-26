package com.momolela.webservice.entity;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author sunzj
 */
public class MyAuthenticator extends Authenticator {
    private final String userName;
    private final String password;

    public MyAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}