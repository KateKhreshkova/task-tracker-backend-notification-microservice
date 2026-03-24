package by.katekhreshkova.notification.consumers;

import by.katekhreshkova.notification.events.UserRegisteredEvent;
import by.katekhreshkova.notification.services.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationEventConsumer {

    private final EmailNotificationService notificationService;

    @KafkaListener(topics = "user-registered-events-topic", groupId = "notification-service")
    public void consume(UserRegisteredEvent event) throws MessagingException {
        notificationService.sendRegistrationEmail(event.email());
    }
}
