package by.katekhreshkova.notification.consumers;

import by.katekhreshkova.notification.events.TaskDailySummaryEvent;
import by.katekhreshkova.notification.services.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailySummaryEventConsumer {
    private final EmailNotificationService notificationService;

    @KafkaListener(topics = "tasks-daily-summary-events-topic", groupId = "notification-service")
    public void consume(TaskDailySummaryEvent event) throws MessagingException {
        notificationService.sendDailySummary(event);
    }
}
