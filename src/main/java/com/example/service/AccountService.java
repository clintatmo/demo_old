package com.example.service;


import com.example.model.Account;

import java.util.Collection;

/* * * @author Clint Atmosoerodjo #commander *  */
public interface AccountService {

    Collection<Account> findAll();

    Account findByUsername(String userename);

    Account createNewAccount(Account account);


}
