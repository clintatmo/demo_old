package com.example.activiti.listener;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rpique on 12/14/2015.
 */
@Component
public class ManagerAssignmentHandler implements TaskListener {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
//TODO DELETE THIS LISTENER NOT DOING ANYTHING
    public void notify(DelegateTask delegateTask) {
        // Execute custom identity lookups here

        // and then for example call following methods:
        //System.out.println("adding kermit as manager of " + delegateTask.getAssignee());
        //taskService.addUserIdentityLink(delegateTask.getId(), "kermit", IdentityLinkType.PARTICIPANT);
    }

}