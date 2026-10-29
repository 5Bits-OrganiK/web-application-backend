package _5bits.organik.platform.profiles.domain.model.commands;

import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;

public record CreateProfileCommand(
        Language language,
        ExpirationThreshold warningThreshold,
        ExpirationThreshold criticalThreshold
) {
    public CreateProfileCommand {
        if (language == null || warningThreshold == null || criticalThreshold == null) {
            throw new IllegalArgumentException("All profile parameters are required");
        }
    }
}
