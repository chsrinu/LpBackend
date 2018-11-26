package com.lp.spring.service;

import com.lp.spring.model.CallEntity;
import com.lp.spring.model.MessageEntity;

import java.util.HashMap;
import java.util.Set;

public interface CallOrMessageLogService {
    void saveLog(CallEntity callEntity);
    void saveLog(MessageEntity messageEntity);

    HashMap<String, Set> loadUserData(String username);

    void deleteLog(String logId);
}
