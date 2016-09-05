package com.example.services.implementations;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.PasswordService;
import com.example.services.TokenService;
import com.example.services.UserService;
import com.example.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by catmosoerodjo on 9/4/16.
 */

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordService passwordService;

    @Autowired
    UserService userService;

    @Value("${jwt.token.secret}")
    String secret;

    @Override
    public UserDetails verifyJwt(ServletRequest request) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Map<String, String> headers = parseHeaders(request);
        String jwt = headers.get(Constant.API_KEY);

        if(jwt != null){

            try {
                final JWTVerifier jwtVerifier = new JWTVerifier(secret);
                final Map<String,Object> claims= jwtVerifier.verify(jwt);
                String uname = claims.get("username").toString();
                String pword = claims.get("password").toString();

                User user = userService.findUserByUsername(uname);

                if (passwordService.matchPassword(pword, user.getPassword())) {
                    user.setPassword(pword);
                    return user;
                }


            } catch (JWTVerifyException e) {
                // Invalid Token
                return null;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> parseHeaders(ServletRequest servletRequest) {
        Map<String, String> headers = new LinkedHashMap<>(2);

        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();

                switch (headerName) {
                    case Constant.USERNAME:
                        headers.put(Constant.USERNAME, request.getHeader(Constant.USERNAME));
                        break;
                    case Constant.API_KEY:
                        headers.put(Constant.API_KEY, request.getHeader(Constant.API_KEY));
                        break;
                }
            }
        }
        return headers;
    }
}
