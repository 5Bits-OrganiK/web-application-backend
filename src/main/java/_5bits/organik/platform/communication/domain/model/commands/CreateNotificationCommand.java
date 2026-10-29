package _5bits.organik.platform.communication.domain.model.commands;

import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;

public record CreateNotificationCommand(
        NotificationType type,
        Severity severity,
        NotificationTitle title
) {
    public CreateNotificationCommand {
        if (type == null || severity == null || title == null) {
            throw new IllegalArgumentException("All notification parameters are required");
        }
    }
}
