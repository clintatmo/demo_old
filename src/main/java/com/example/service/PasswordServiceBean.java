package com.example.service;

import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/* * * @author Clint Atmosoerodjo #commander *  */

@Service
public class PasswordServiceBean implements PasswordService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generatePassword(List<CharacterRule> characterRuleList, Integer passwordLength) {

        if (characterRuleList != null) {

            PasswordGenerator generator = new PasswordGenerator();
            String password = generator.generatePassword(passwordLength, characterRuleList);
            return password != null ? password : "user";
        }

        return null;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String createPassword(String password, List<CharacterRule> characterRuleList, Integer passwordLength) {

        String generatedPassword = generatePassword(characterRuleList, passwordLength);

        return encryptPassword(generatedPassword);
    }

    @Override
    public Boolean matchPassword(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

}