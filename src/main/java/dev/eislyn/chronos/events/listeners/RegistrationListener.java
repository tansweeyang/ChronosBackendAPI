package dev.eislyn.chronos.events.listeners;

import dev.eislyn.chronos.model.User;
import dev.eislyn.chronos.events.OnRegistrationCompleteEvent;
import dev.eislyn.chronos.service.IUserAuthService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserAuthService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Welcome to AutoTodo! Confirm your Registration";
        String confirmationUrl = event.getAppUrl() + "/api/auth/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        try {
            sendHtmlEmail(recipientAddress, subject, message, confirmationUrl);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHtmlEmail(String to, String subject, String message, String confirmationUrl) throws MessagingException, jakarta.mail.MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        // Load template as InputStream (works both from file system and inside a JAR)
        Resource resource = new ClassPathResource("templates/email/registration-confirmation.html");

        InputStream inputStream = resource.getInputStream();  // Use InputStream instead of getFile()
        String htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        inputStream.close();  // Close the input stream

        // Replace placeholders with actual values
        htmlContent = htmlContent.replace("${message}", message);
        htmlContent = htmlContent.replace("${confirmationUrl}", confirmationUrl);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // true indicates HTML content

        mailSender.send(mimeMessage);
    }
}
