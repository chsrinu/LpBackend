package com.lp.spring.dao;

import com.lp.spring.model.CallEntity;
import com.lp.spring.model.MessageEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public class CallOrMessageLogRepositoryImpl implements CallOrMessageLogRepository {
    Logger LOG = Logger.getLogger(CallOrMessageLogRepositoryImpl.class);
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void saveLog(CallEntity callEntity) {
        getSession().save(callEntity);
    }

    @Override
    public void saveLog(MessageEntity messageEntity) {
        getSession().save(messageEntity);
    }

    @Override
    public HashMap<String, Set> loadUserCallData(String username) {
        Session session = getSession();
        HashMap<String, Set> temp= new HashMap<>();
        String callListQuery = "FROM logs l WHERE  l.message is null";
        String messageListQuery = "FROM logs l WHERE l.message is not null";
         temp.put("calls", new HashSet(session.createQuery(callListQuery).getResultList()));
         temp.put("messages", new HashSet(session.createQuery(messageListQuery).getResultList()));
         return temp;
    }

    @Override
    public void deleteLog(String logId) {
        String deleteLog = "DELETE FROM logs l WHERE  l.logId = :logId";
        Query query = getSession().createQuery(deleteLog);
        query.setParameter("logId",logId);
        int result = query.executeUpdate();
        LOG.info("Rows affected: " + result);
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
