package com.lp.spring.dao;

import com.lp.spring.model.CallEntity;
import com.lp.spring.model.MessageEntity;

import java.util.HashMap;
import java.util.Set;

public interface CallOrMessageLogRepository {
    void saveLog(CallEntity callEntity);
    void saveLog(MessageEntity messageEntity);

    HashMap<String, Set> loadUserCallData(String username);

    void deleteLog(String logId);
}
