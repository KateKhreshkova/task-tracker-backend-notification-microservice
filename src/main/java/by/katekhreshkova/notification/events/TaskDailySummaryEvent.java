package by.katekhreshkova.notification.events;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDailySummaryEvent(UUID userId,
                                    String email,
                                    int completed,
                                    int remaining,
                                    LocalDate date) {
}
