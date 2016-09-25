package com.example.activiti.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

/**
 * Created by qualogy on 2/22/2016.
 */
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
