package _5bits.organik.platform.inventory.domain.model.valueobjects;

import java.time.LocalDate;

public record ExpirationDate(LocalDate value) {
    private static final String NOT_NULL_MESSAGE_KEY = "inventory.error.expirationDate.notNull";

    public ExpirationDate {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE_KEY);
        }
    }

}
