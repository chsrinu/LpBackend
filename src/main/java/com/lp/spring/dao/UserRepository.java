package com.lp.spring.dao;

import com.lp.spring.model.User;


public interface UserRepository {
    void createUser(User user);
    User readUser(String userName, boolean initializeListFlag);
    void updateUser(User updatedUser);
    void deleteUser(String userName);
    User loadUser(String userName);
}
