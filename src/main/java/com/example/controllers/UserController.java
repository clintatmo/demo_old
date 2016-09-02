package com.example.controllers;

import com.example.dtos.UserDto;
import com.example.entities.User;
import com.example.entities.VerificationToken;
import com.example.events.OnRegistrationCompleteEvent;
import com.example.handlers.EmailExistsException;
import com.example.handlers.UserAlreadyExistException;
import com.example.services.PasswordService;
import com.example.services.UserService;
import com.example.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by clint on 8/31/16.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordService passwordService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }



    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User with email " + user.getEmail());

        if (userService.isUserExist(user)) {
            System.out.println("A User with email " + user.getEmail() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        User currentUser = userService.findById(id);

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setEmail(user.getEmail());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- User registration --------------------------------------------------------

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> registerUserAccount(@RequestBody @Valid final UserDto account, HttpServletRequest request) throws EmailExistsException {
        //LOGGER.debug("Registering user account with information: {}", account);

        String generatedPassword = passwordService.generatePassword(Constant.PASSWORD_RULES, Constant.PASSWORD_LENGTH);

        final User registered = createUserAccount(account, generatedPassword);
        if (registered == null) {
            throw new UserAlreadyExistException();
        }

        registered.setPassword(generatedPassword);
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));

        return new ResponseEntity<String>( generatedPassword, HttpStatus.OK);

    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public void confirmRegistration(@RequestParam("token") final String token, final HttpServletRequest request, HttpServletResponse response) throws IOException {
        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            //LOGGER.debug("Invalid token");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            //LOGGER.debug("Token expired");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        user.isEnabled();
        userService.updateUser(user);
        //LOGGER.debug("Account verified");
        response.setStatus(HttpServletResponse.SC_OK);
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        response.sendRedirect(appUrl + "#/signin");
    }

    // user activation - verification

    @RequestMapping(value = "/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public void resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken, HttpServletResponse response) throws UnsupportedEncodingException {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        final SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, newToken, user);
        mailSender.send(email);

        //LOGGER.debug("Account verified");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //------------------- Non API --------------------------------------------------------

    private final SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final VerificationToken newToken, final User user) throws UnsupportedEncodingException {
        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(" \r\n" + confirmationUrl);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private User createUserAccount(final UserDto account, String generatedPassword) throws EmailExistsException {
        User registered = null;
        registered = userService.registerNewUserAccount(account, generatedPassword);
        return registered;
    }



}
