package dev.eislyn.chronos.events.listeners;

import dev.eislyn.chronos.model.User;
import dev.eislyn.chronos.events.OnPasswordResetRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetRequestEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnPasswordResetRequestEvent event) {
        this.sendResetTokenEmail(event);
    }

    private void sendResetTokenEmail(OnPasswordResetRequestEvent event) {
        User user = event.getUser();
        String token = event.getToken();
        String recipientAddress = user.getEmail();
        String subject = "Reset Password";
        String resetPasswordUrl = event.getAppUrl() + "/user/changePassword?token=" + token;
        String message = messageSource.getMessage("message.resetPassword", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + resetPasswordUrl);
        email.setFrom(env.getProperty("support.email"));

        try {
            mailSender.send(email);
        } catch (MailException e) {
            // Handle the email sending failure if necessary
        }
    }
}
