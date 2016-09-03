package com.example.services;

import com.example.dtos.UserDto;
import com.example.entities.User;
import com.example.entities.VerificationToken;
import com.example.handlers.EmailExistsException;

import java.util.List;

/**
 * Created by clint on 8/31/16.
 */
public interface UserService {

    User findById(long id);

    User findByEmail(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    List<User> findAllUsers();

    void deleteAllUsers();

    boolean isUserExist(User user);

    User registerNewUserAccount(UserDto account, String generatedPassword) throws EmailExistsException;

    VerificationToken getVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String existingToken);

    User getUser(String token);

    void createVerificationTokenForUser(User user, String token);

    User findUserByUsername(String name);
}
