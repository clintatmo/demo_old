package com.example.activiti.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

/* * * @author Clint Atmosoerodjo #commander *  */
public class ActivitiUserManagerFactory implements SessionFactory {
    @Override
    public Class<?> getSessionType() {
        return ActivitiUserManager.class;
    }

    @Override
    public Session openSession() {
        return new ActivitiUserManager();
    }
}
