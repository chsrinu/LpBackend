package com.lp.spring.service;

import com.lp.spring.dao.CallOrMessageLogRepository;
import com.lp.spring.model.CallEntity;
import com.lp.spring.model.MessageEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class CallOrMessageLogServiceImpl implements CallOrMessageLogService{
    Logger LOG = Logger.getLogger(CallOrMessageLogServiceImpl.class);
    @Autowired
    CallOrMessageLogRepository callOrMessageLogRepository;
    @Override
    public void saveLog(CallEntity callEntity) {
        createAndSetLogId(callEntity);
        callOrMessageLogRepository.saveLog(callEntity);
    }
    private void createAndSetLogId(CallEntity callEntity) {
        callEntity.setLogId(callEntity.getUser().getUserName()+"_"+callEntity.getUser().getCallLogs().size());
    }

    @Override
    public void saveLog(MessageEntity messageEntity) {
        createAndSetLogId(messageEntity);
        callOrMessageLogRepository.saveLog(messageEntity);
    }

    @Override
    public HashMap<String, Set> loadUserData(String username) {
        return callOrMessageLogRepository.loadUserCallData(username);
    }

    @Override
    public void deleteLog(String logId) {
        callOrMessageLogRepository.deleteLog(logId);
    }
}
