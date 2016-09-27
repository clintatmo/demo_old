package com.example.activiti.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

/**

 * @author catmosoerodjo
 *
 */
public class ActivitiGroupManagerFactory implements SessionFactory {
    @Override
    public Class<?> getSessionType() {
        return ActivitiGroupManager.class;
    }

    @Override
    public Session openSession() {
        return new ActivitiGroupManager();
    }
}
