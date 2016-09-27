package com.example.config;

import com.example.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/* * * @author Clint Atmosoerodjo #commander *  */

@Configuration
public class JavaMailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender javaMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(env.getProperty("smtp.host"));
        mailSenderImpl.setPort(env.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(env.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(env.getProperty("smtp.username"));
        mailSenderImpl.setPassword(env.getProperty("smtp.password"));
        final Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", false);
        javaMailProps.put("mail.smtp.starttls.enable", false);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

    private final SimpleMailMessage message(final Account user) {

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("");
        email.setSubject("");
        email.setText("");
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}