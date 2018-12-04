package com.example.demo.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Properties;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    static int num = 0;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    public String checkMail() {

        final String body = "TEST Mail";
        final String subject = "TEST Subject";

        final String toEmail = "";
        final String ccEmail = "";
        final String bccEmail = "";
        final String from = env.getProperty("mail.smtp.from", String.class, "");
        final String username = env.getProperty("mail.smtp.user", String.class, "");
        final String password = env.getProperty("mail.smtp.pass", String.class, "");
        final String charset = env.getProperty("mail.charset", String.class, "");
        final String encoding = env.getProperty("mail.encoding", String.class, "");

        Properties props = new Properties();
        props.put("mail.smtp.host", env.getProperty("mail.smtp.host", String.class, ""));
        props.put("mail.smtp.port", env.getProperty("mail.smtp.port", String.class, ""));
        props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth", String.class, ""));
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable", String.class, ""));
        props.put("mail.smtp.connectiontimeout", env.getProperty("mail.smtp.connectiontimeout", String.class, ""));
        props.put("mail.smtp.timeout", env.getProperty("mail.smtp.timeout", String.class, ""));
        props.put("mail.debug", env.getProperty("mail.debug", String.class, ""));

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, env.getProperty("mail.send.from.name", String.class, "")));
            message.setReplyTo(new Address[] { new InternetAddress(from) });
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            if (!Strings.isNullOrEmpty(ccEmail)) {
                message.setRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
            }

            if (!Strings.isNullOrEmpty(bccEmail)) {
                message.setRecipient(Message.RecipientType.BCC, new InternetAddress(bccEmail));
            }

            // 件名
            message.setSubject(subject, charset);

            String contentType = "plain";
            switch (0) {
                case 0 :
                    contentType = "plain";
                    break;
                case 1 :
                    contentType = "html";
                    break;
                default :
                    break;
            }

            // メール本文
            message.setText(body, charset, contentType);

            message.setHeader("Content-Transfer-Encoding", encoding);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        num++;
        return String.valueOf(num);
    }

    @RequestMapping(value = "/mail-port", method = RequestMethod.GET)
    public String checkMailPort() {
        String hostName = "localhost" ;
        int port = 25;
        try {
            Socket sock = new Socket(hostName, port);
            System.out.println("Open:" + port) ;
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }
}
