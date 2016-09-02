package com.example.events;

import com.example.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by catmosoeredjo on 2/16/2016.
 */
public class OnPasswordResetCompleteEvent  extends ApplicationEvent {

    private final String appUrl;
    private final User user;

    public OnPasswordResetCompleteEvent(final User user, final String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }
    public User getUser() {
        return user;
    }

}
