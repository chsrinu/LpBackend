package com.lp.spring.service;

import com.lp.spring.dao.UserRepositoryImpl;
import com.lp.spring.exceptions.InvalidPasswordException;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.exceptions.RegistrationException;
import com.lp.spring.model.PasswordUpdate;
import com.lp.spring.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    UserRepositoryImpl userRepositoryImpl;
    @Mock
    User user;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl service= new UserServiceImpl();
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test(expected = RegistrationException.class)
    public void CreateUserTestForExistingUser() throws RegistrationException, PasswordLengthException {
        when(user.getUserName()).thenReturn("7416");
        when(userRepositoryImpl.readUser("7416",false)).thenReturn(user);//user already existing throw RegistrationException
        service.createUser(user);
    }
    @Test(expected = PasswordLengthException.class)
    public void CreateUserTestForPasswordLengthException() throws RegistrationException, PasswordLengthException {
        when(user.getUserName()).thenReturn("7416");
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(null);
        when(user.getPassword()).thenReturn("1234567890123456312345");
        service.createUser(user);
    }
    @Test
    public void CreateUserTestSuccessfully() throws RegistrationException, PasswordLengthException {
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(null);
        when(user.getUserName()).thenReturn("7416");
        when(user.getPassword()).thenReturn("1234567890");
        when(user.getRoles()).thenReturn(new HashSet<>());
        when(passwordEncoder.encode(anyString())).thenReturn("asdasdasdadasd");
        service.createUser(user);
        verify(userRepositoryImpl,atLeastOnce()).createUser(user);
    }
    @Test(expected = UsernameNotFoundException.class)
    public void deleteUserTestWithUserNotFound(){
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(null);
        service.deleteUser("7416");
    }
    @Test
    public void deleteUserTestWithUserExisting(){
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(user);
        service.deleteUser("7416");
        verify(userRepositoryImpl,atLeastOnce()).deleteUser(anyString());
    }
    @Test
    public void loadUserProfileTest(){
        User user = new User();
        user.setCountryCode("91");
        user.setFirstName("Srinivas");
        user.setLastName("Cheerla");
        user.setUserName("7416");
        user.setPassword("1234567890");
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(user);
        HashMap<String, String> profileMap = service.getUserProfile("7416");
        Assert.assertEquals(profileMap.size(),4);
        Assert.assertEquals(profileMap.get("userName"),user.getUserName());
        Assert.assertEquals(profileMap.get("countryCode"),user.getCountryCode());
        Assert.assertEquals(profileMap.get("firstName"),user.getFirstName());
        Assert.assertEquals(profileMap.get("lastName"),user.getLastName());
    }
    @Test(expected = InvalidPasswordException.class)
    public void changePasswordTestWithIncorrectPassword() throws PasswordLengthException, InvalidPasswordException {
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(user);
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(false);

        service.changePassword("7416",new PasswordUpdate());
    }
    @Test(expected = PasswordLengthException.class)
    public void changePasswordTestWithIncorrectLengthPassword() throws PasswordLengthException, InvalidPasswordException {
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(user);
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        PasswordUpdate passwordUpdate = new PasswordUpdate();
        passwordUpdate.setNewPassword("123");
        passwordUpdate.setCurrentPassword("123");
        service.changePassword("7416",passwordUpdate);
    }
    @Test
    public void changePasswordSuccessfully() throws PasswordLengthException, InvalidPasswordException {
        when(userRepositoryImpl.readUser(anyString(),anyBoolean())).thenReturn(user);
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        PasswordUpdate passwordUpdate = new PasswordUpdate();
        passwordUpdate.setNewPassword("123456789");
        passwordUpdate.setCurrentPassword("123");
        service.changePassword("7416",passwordUpdate);
        verify(userRepositoryImpl,atLeastOnce()).updateUser(user);
    }
}
