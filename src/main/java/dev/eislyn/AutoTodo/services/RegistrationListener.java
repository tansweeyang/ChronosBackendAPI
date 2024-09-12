package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserRegistrationService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEntity user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Welcome to AutoTodo! Confirm your Registration";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        try {
            sendHtmlEmail(recipientAddress, subject, message, confirmationUrl);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHtmlEmail(String to, String subject, String message, String confirmationUrl) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        // Create HTML content
        String htmlContent = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; margin: 0; padding: 0; }" +
                "table { width: 100%; max-width: 600px; margin: 0 auto; border-collapse: collapse; }" +
                ".header img { width: 100%; height: auto; max-height: 150px; object-fit: contain; }" +
                ".content { padding: 20px; }" +
                ".button { display: inline-block; padding: 10px 20px; margin: 20px 0; color: #fff; background-color: #28a745; text-decoration: none; border-radius: 5px; }" +
                ".footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #6c757d; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table>" +
                "   <tr class='header'>" +
                "       <td>" +
                "           <img src='https://www.qries.com/images/banner_logo.png' alt='Company Logo'>" +
                "       </td>" +
                "   </tr>" +
                "   <tr>" +
                "       <td class='content'>" +
                "           <h2>Welcome to AutoTodo, " + message + "!</h2>" +
                "           <p>Thank you for registering with us. We're excited to have you onboard.</p>" +
                "           <p>Please click the button below to confirm your registration:</p>" +
                "           <a href='" + confirmationUrl + "' class='button'>Confirm Registration</a>" +
                "           <p>If you didn’t register with us, please ignore this email.</p>" +
                "           <br>" +
                "           <p>Best regards,<br>The AutoTodo Team</p>" +
                "       </td>" +
                "   </tr>" +
                "   <tr>" +
                "       <td class='footer'>" +
                "           <p>AutoTodo Inc.<br>1234 Your Street, Your City, Your Country</p>" +
                "           <p>&copy; 2024 AutoTodo. All rights reserved.</p>" +
                "       </td>" +
                "   </tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // true indicates HTML content

        mailSender.send(mimeMessage);
    }
}
