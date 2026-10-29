package _5bits.organik.platform.profiles.domain.model.aggregates;

import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language", nullable = false, length = 10)
    private Language language;

    @Column(name = "warning_expiration_days", nullable = false)
    private ExpirationThreshold warningThreshold;

    @Column(name = "critical_expiration_days", nullable = false)
    private ExpirationThreshold criticalThreshold;

    protected Profile() {
    }

    public Profile(Language language, ExpirationThreshold warningThreshold, ExpirationThreshold criticalThreshold) {
        if (criticalThreshold.days() > warningThreshold.days()) {
            throw new IllegalArgumentException("Critical expiration threshold cannot be greater than warning threshold");
        }
        this.language = language;
        this.warningThreshold = warningThreshold;
        this.criticalThreshold = criticalThreshold;
    }

    public void updateSettings(Language language, ExpirationThreshold warningThreshold, ExpirationThreshold criticalThreshold) {
        if (criticalThreshold.days() > warningThreshold.days()) {
            throw new IllegalArgumentException("Critical expiration threshold cannot be greater than warning threshold");
        }
        this.language = language;
        this.warningThreshold = warningThreshold;
        this.criticalThreshold = criticalThreshold;
    }

    public Long getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public ExpirationThreshold getWarningThreshold() {
        return warningThreshold;
    }

    public ExpirationThreshold getCriticalThreshold() {
        return criticalThreshold;
    }
}
