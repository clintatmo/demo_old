package com.example.activiti.listener;

import com.example.dto.HoofdTaak;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.joda.time.LocalDate;

import java.util.Date;

import static com.example.util.Constant.HOOFD_TAAK;

/**
 * Created by rpique on 12/13/2015.
 */
public class IntakeTaskCreateListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        Date dueDate = LocalDate.now().plusDays(5).toDate();
        delegateTask.setDueDate(dueDate);
        HoofdTaak hoofdTaak = new HoofdTaak();
        hoofdTaak.setDueDate(dueDate);
        hoofdTaak.setNaam("intake");
        delegateTask.setVariable(HOOFD_TAAK, hoofdTaak);
    }

}