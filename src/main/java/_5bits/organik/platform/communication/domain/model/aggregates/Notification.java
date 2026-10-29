package _5bits.organik.platform.communication.domain.model.aggregates;

import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationTitle;
import _5bits.organik.platform.communication.domain.model.valueobjects.NotificationType;
import _5bits.organik.platform.communication.domain.model.valueobjects.Severity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 30)
    private NotificationType type;

    @Column(name = "severity", nullable = false, length = 20)
    private Severity severity;

    @Column(name = "title", nullable = false, length = 150)
    private NotificationTitle title;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    protected Notification() {
    }

    public Notification(NotificationType type, Severity severity, NotificationTitle title) {
        this.type = type;
        this.severity = severity;
        this.title = title;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public Long getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public NotificationTitle getTitle() {
        return title;
    }

    public boolean isRead() {
        return isRead;
    }
}
