package com.lp.spring.service;

import com.lp.spring.dao.CallOrMessageLogRepository;
import com.lp.spring.dao.CallOrMessageLogRepositoryImpl;
import com.lp.spring.model.CallEntity;
import com.lp.spring.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CallOrMessageLogServiceImplTest {
    @Mock
    CallOrMessageLogRepositoryImpl callOrMessageLogRepository;
    @InjectMocks
    CallOrMessageLogServiceImpl callOrMessageLogService = new CallOrMessageLogServiceImpl();
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void checkLogIdFormat(){
        User user = new User();
        CallEntity entity = new CallEntity();
        user.setUserName("7416");
        user.setCallLogs(new HashSet<>());
        entity.setUser(user);
        callOrMessageLogService.saveLog(entity);
        verify(callOrMessageLogRepository,atLeastOnce()).saveLog(entity);
        Assert.assertEquals("7416_0",entity.getLogId());
    }
}
