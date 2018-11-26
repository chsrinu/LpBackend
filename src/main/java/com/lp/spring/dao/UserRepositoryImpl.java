package com.lp.spring.dao;

import com.lp.spring.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository{
    private Logger logger = Logger.getLogger(UserRepositoryImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void createUser(User user) {
        getSession().save(user);
    }

    @Override
    public User readUser(String userName, boolean initializeListFlag) {
        User user = getSession().get(User.class,userName);
        if(initializeListFlag)
            user.getCallLogs().size();
        return user;
    }

    @Override
    public void updateUser(User updatedUser) {
        getSession().update(updatedUser);
    }

    @Override
    public void deleteUser(String userName) {
        Session session = getSession();
        User user = session.get(User.class, userName);
        session.delete(user);
    }

    @Override
    public User loadUser(String userName) {
        return getSession().load(User.class, userName);
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
