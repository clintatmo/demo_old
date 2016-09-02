package com.example.utils;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.util.Arrays;
import java.util.List;

/**
 * Created by clint on 9/2/16.
 */
public class Constant {
    public static final String USER_ROLE = "user";
    public static final int PASSWORD_UPPERCASE_MAX = 2;
    public static final int PASSWORD_NUMBERS_MAX = 2;
    public static int PASSWORD_LENGTH = 8;

    public static final List<CharacterRule> PASSWORD_RULES = Arrays.asList(
            // at least one upper-case character
            new CharacterRule(EnglishCharacterData.UpperCase, Constant.PASSWORD_UPPERCASE_MAX),
            // at least one digit character
            new CharacterRule(EnglishCharacterData.Digit, Constant.PASSWORD_NUMBERS_MAX));
}
