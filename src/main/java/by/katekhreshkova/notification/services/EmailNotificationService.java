package by.katekhreshkova.notification.services;

import by.katekhreshkova.notification.events.TaskDailySummaryEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    private final JavaMailSender mailSender;
    private final static String FROM = "ii5myca93@mozmail.com";

    public void sendRegistrationEmail(String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(FROM);
        helper.setTo(email);
        helper.setSubject("Welcome to TaskFlow!");
        helper.setText("<h1> Hi!\n" +
                "\n" +
                "            Welcome to our service \uD83C\uDF89\n" +
                "\n" +
                "            Now you can create a task and track your progress</h1>", true);

        mailSender.send(message);
    }

    public void sendDailySummary(TaskDailySummaryEvent event) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(FROM);
        helper.setTo(event.email());
        helper.setSubject("Your Daily Task Summary");

        String html = buildHtml(event);

        helper.setText(html, true); // true = HTML

        mailSender.send(message);
    }

    private String buildHtml(TaskDailySummaryEvent event) {
        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color:#f5f5f5; padding:20px;">
            
            <div style="max-width:600px; margin:0 auto; background:white; padding:20px; border-radius:10px;">
                
                <h2 style="color:#333;">📊 Daily Summary</h2>
                
                <p style="color:#666;">Date: %s</p>

                <div style="margin-top:20px;">
                    
                    <p style="font-size:16px;">
                        ✅ Completed tasks: <b>%d</b>
                    </p>
                    
                    <p style="font-size:16px;">
                        ⏳ Remaining tasks: <b>%d</b>
                    </p>

                </div>

                <hr style="margin:20px 0;"/>

                <p style="color:#888; font-size:14px;">
                    Keep going! You're doing great 🚀
                </p>

            </div>

        </body>
        </html>
        """
                .formatted(
                        event.date(),
                        event.completed(),
                        event.remaining()
                );
    }
}
