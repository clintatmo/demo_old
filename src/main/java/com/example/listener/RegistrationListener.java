package com.example.listener;

import com.example.entities.User;
import com.example.events.OnRegistrationCompleteEvent;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructRegistrationConfirmationEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private final SimpleMailMessage constructRegistrationConfirmationEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/user/registrationConfirm?token=" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Gaarne uw account eerst te activeren middels de volgende link."+ " \r\n \r\n" +confirmationUrl + " \r\n \r\n" + "U kunt bij de eerste inlog met de volgende gebruikersnaam/wachtwoord combinatie inloggen." + " \r\n \r\n" + "Gebruikersnaam: " + event.getUser().getUsername() + " \r\n" + "Wachtwoord: " + event.getUser().getPassword());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}