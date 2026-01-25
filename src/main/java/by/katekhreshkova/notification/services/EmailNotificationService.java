package by.katekhreshkova.notification.services;

import by.katekhreshkova.notification.events.TaskDailySummaryEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    private final MailService mailService;
    private final TemplateService templateService;

    public void sendVerificationEmail(String email, String token) throws MessagingException {
        String link = "https://auth-service/verify?token=" + token;

        String html = templateService.render("verify-email.html",
                Map.of("link", link));

        mailService.send(email, "Verify your account", html);
    }

    public void sendDailySummary(TaskDailySummaryEvent event) throws MessagingException {
        String html = templateService.render("daily-summary.html",
                Map.of(
                        "completed", event.completed(),
                        "remaining", event.remaining(),
                        "date", event.date().toString()
                )
        );

        mailService.send(event.email(), "Your daily tasks summary", html);
    }
}
