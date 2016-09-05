package com.example.services;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

public interface TokenService {

    UserDetails verifyJwt(ServletRequest request) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException;

    Map<String, String> parseHeaders(ServletRequest servletRequest);
}