package com.example.controllers;

import com.auth0.jwt.JWTSigner;
import com.example.dtos.CredentialsDto;
import com.example.services.UserService;
import com.example.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private UserService userService;


    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Value("${jwt.token.issuer}")
    String issuer;
    @Value("${jwt.token.secret}")
    String secret;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody CredentialsDto authenticationRequest) throws AuthenticationException {

        // Perform the authentication
        Authentication requestAuth = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication resultAuth = authenticationManagerBean.authenticate(requestAuth);
        SecurityContextHolder.getContext().setAuthentication(resultAuth);

        // generate Token (User ID, Authorities, Device Type, Created Date)

        final JWTSigner signer = new JWTSigner(secret);
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", issuer);
        claims.put("exp", Constant.IAT + Constant.EXP);
        claims.put("iat", Constant.IAT);
        claims.put("username", authenticationRequest.getUsername());
        claims.put("password",  authenticationRequest.getPassword());

        final String jwt = signer.sign(claims);

        return ResponseEntity.ok(jwt);
    }

    /* @RequestMapping(value = "/signin", method = RequestMethod.POST)
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
    }*/

}