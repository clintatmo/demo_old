package com.example.controllers;

import com.example.dtos.CredentialsDto;
import com.example.entities.User;
import com.example.services.UserService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<HashMap<Object, Object>> authenticatedUser(@RequestBody CredentialsDto credentials) {
        boolean validCredentials = credentials == null || credentials.getUsername() == null || credentials.getPassword() == null;
        if (validCredentials) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        token.setDetails(new WebAuthenticationDetails(new Request()));
        try {
            Authentication authenticatedUser = authenticationManagerBean.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            if (!authenticatedUser.isAuthenticated()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            HashMap<Object, Object> objectHashMapp = new HashMap<>();
            objectHashMapp.put("auth", authenticatedUser);
            User user = userService.findUserByUsername(authenticatedUser.getName());
            objectHashMapp.put("firstLogin", user.isFirstLogin());

            return new ResponseEntity<HashMap<Object, Object>>( objectHashMapp, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET, produces = "application/json")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}