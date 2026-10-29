package _5bits.organik.platform.profiles.domain.model.commands;

import _5bits.organik.platform.profiles.domain.model.valueobjects.ExpirationThreshold;
import _5bits.organik.platform.profiles.domain.model.valueobjects.Language;

public record UpdateProfileSettingsCommand(
        Long profileId,
        Language language,
        ExpirationThreshold warningThreshold,
        ExpirationThreshold criticalThreshold
) {
    public UpdateProfileSettingsCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("Profile ID must be a positive number");
        }
        if (language == null || warningThreshold == null || criticalThreshold == null) {
            throw new IllegalArgumentException("All profile configuration parameters are required");
        }
    }
}
