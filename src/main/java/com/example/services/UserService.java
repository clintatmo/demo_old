package com.example.services;

import com.example.entities.User;

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
}
