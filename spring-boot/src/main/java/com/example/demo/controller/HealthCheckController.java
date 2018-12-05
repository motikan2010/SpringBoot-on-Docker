package com.example.demo.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import java.util.Properties;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @Autowired
    private Environment env;

    /**
     * メールのコネクション死活チェック
     *
     * @return
     */
    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    public ResponseEntity<ImmutableMap> checkMail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", env.getProperty("mail.smtp.host", String.class, ""));
        props.put("mail.smtp.port", env.getProperty("mail.smtp.port", String.class, ""));
        props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth", String.class, ""));

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("", "");
                }
            });
            session.setDebug(false);

            Transport transport = session.getTransport("smtp");
            transport.connect();
        } catch (MessagingException ex) {
            return new ResponseEntity<>(ImmutableMap.of("status", "Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ImmutableMap.of("status", "Success"), HttpStatus.OK);
    }
}
