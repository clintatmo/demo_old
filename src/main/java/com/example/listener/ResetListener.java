package com.example.listener;


import com.example.entities.User;
import com.example.events.OnPasswordResetCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ResetListener implements ApplicationListener<OnPasswordResetCompleteEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnPasswordResetCompleteEvent event) {
        this.confirmPasswordReset(event);
    }

    private void confirmPasswordReset(final OnPasswordResetCompleteEvent event) {
        final User user = event.getUser();

        final SimpleMailMessage email = constructPasswirdResetConfirmationEmailMessage(event, user);
        mailSender.send(email);
    }

    private final SimpleMailMessage constructPasswirdResetConfirmationEmailMessage(final OnPasswordResetCompleteEvent event, final User user) {
        final String recipientAddress = user.getEmail();
        final String subject = "Password Reset Confirmation";
        final String signinUrl = event.getAppUrl() + "/#/signin";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Uw wachtwoord is gereset! U kunt met de volgende link inloggen." + " \r\n \r\n"  +signinUrl + " \r\n \r\n" + "U kunt bij de eerste inlog met de volgende gebruikersnaam/wachtwoord combinatie inloggen." + " \r\n \r\n" + "Gebruikersnaam: " + event.getUser().getUsername() + " \r\n" + "Wachtwoord: " + event.getUser().getPassword());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}