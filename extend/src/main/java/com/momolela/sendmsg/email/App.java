package com.momolela.sendmsg.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/10/31 10:27
 */
public class App {

    static class MyAuthenticator extends Authenticator {
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


    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        String userName = "1083910359@qq.com";
        String password = "lpykkpcteabpiade";
        Authenticator authenticator = new MyAuthenticator(userName, password);

        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, authenticator);
        // 可以从控制台看到信息
        session.setDebug(true);

        try {
            Address from = new InternetAddress(userName);
            Address to = new InternetAddress("1548011224@qq.com");

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);
            msg.setSubject("创业慧康一体化平台通知");
            msg.setContent("您好，你收到了创业慧康一体化平台通知，请注意查收", "text/html;charset=utf-8");
            msg.setSentDate(new Date());
            msg.setRecipient(Message.RecipientType.TO, to);
            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
