package com.example.services.implementations;

import com.example.dtos.UserDto;
import com.example.entities.Authority;
import com.example.entities.User;
import com.example.entities.VerificationToken;
import com.example.handlers.EmailExistsException;
import com.example.repositories.AuthorityRepository;
import com.example.repositories.UserRepository;
import com.example.repositories.VerificationTokenRepository;
import com.example.services.PasswordService;
import com.example.services.UserService;
import com.example.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by clint on 8/31/16.
 */

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    @Override
    public User findById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public boolean isUserExist(User user) {
        return userRepository.exists(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        User account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return createUser(account);
    }

    private UserDetails createUser(User account) {

        if (!account.isEnabled()) {
            return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), false, true,
                    true, true, createAuthority(account)) {
            };
        }

        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), true, true,
                true, true, createAuthority(account)) {
        };
    }

    private Collection<? extends GrantedAuthority> createAuthority(User account) {

        User user = new User();
        List<String> authorityList = new ArrayList<String>();
        String authority;
        Set<Authority> authoritySet = account.getAuthoritySet();
        Iterator<Authority> authoritySetIterator = authoritySet.iterator();

        while (authoritySetIterator.hasNext()) {
            authority = authoritySetIterator.next().getAuthority();
            authorityList.add(authority);
        }

        user.setUserAuthorities(authorityList);

        return user.getAuthorities();
    }

    public User registerNewUserAccount(UserDto account, String generatedPassword) throws EmailExistsException {
        if (emailExist(account.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + account.getEmail());
        }
        final User user = new User();

        user.setUsername(account.getUsername());
        user.setPassword(passwordService.encryptPassword(generatedPassword));
        user.setEmail(account.getEmail());
        user.isEnabled();

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findByAuthority(Constant.USER_ROLE));

        user.setAuthoritySet(authorities);
        return userRepository.save(user);
    }

    private boolean emailExist(final String email) {
        final User user = userRepository.findByEmail(email);
        return user != null;
    }

    public User getUser(final String verificationToken) {
        final User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }
        return auth.getPrincipal().toString();
    }

    public VerificationToken getVerificationToken(final String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(getUsername());
    }
}
