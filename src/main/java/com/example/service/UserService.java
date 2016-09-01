package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by clint on 8/31/16.
 */
public interface UserService {

    User findById(long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    List<User> findAllUsers();

    void deleteAllUsers();

    boolean isUserExist(User user);
}
