package _5bits.organik.platform.inventory.domain.model.valueobjects;

public enum MovementType {
    ENTRY,
    EXIT,
    ADJUSTMENT,
    WASTE;

    public boolean requiresReason() {
        return this == ADJUSTMENT || this == WASTE;
    }
}
