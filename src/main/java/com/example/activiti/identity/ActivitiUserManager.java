package com.example.activiti.identity;

import com.example.config.CustomUserDetailsService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/* * * @author Clint Atmosoerodjo #commander *  */
public class ActivitiUserManager extends AbstractManager implements UserIdentityManager {

    @Autowired
    private CustomUserDetailsService userService;

    @Override
    public User createNewUser(String userId) {
        throw new UnsupportedOperationException("Create user not supported through activiti API");
    }

    @Override
    public void insertUser(User user) {
        throw new UnsupportedOperationException("Insert user not supported through activiti API");
    }

    @Override
    public void updateUser(User updatedUser) {

    }

    @Override
    public User findUserById(String userId) {

        UserDetails user = userService.loadUserByUsername(userId);

        User activitiUser = new UserEntity();
        activitiUser.setId(user.getUsername());

        return activitiUser;
    }

    @Override
    public void deleteUser(String userId) {
        throw new UnsupportedOperationException("delete User  not supported through activiti API");
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        return null;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return 0;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        return null;
    }

    @Override
    public UserQuery createNewUserQuery() {
        return null;
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        return null;
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        return null;
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        return null;
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        return null;
    }

    @Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0;
    }

    @Override
    public boolean isNewUser(User user) {
        return false;
    }

    @Override
    public Picture getUserPicture(String userId) {
        return null;
    }

    @Override
    public void setUserPicture(String userId, Picture picture) {

    }
}
