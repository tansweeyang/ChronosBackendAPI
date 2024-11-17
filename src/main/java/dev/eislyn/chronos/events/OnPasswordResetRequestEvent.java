package dev.eislyn.chronos.events;

import dev.eislyn.chronos.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnPasswordResetRequestEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;
    private final String token;

    public OnPasswordResetRequestEvent(User user, Locale locale, String appUrl, String token) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.token = token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
