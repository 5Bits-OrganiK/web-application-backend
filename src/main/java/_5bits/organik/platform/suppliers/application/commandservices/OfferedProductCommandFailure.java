package _5bits.organik.platform.suppliers.application.commandservices;

public sealed interface OfferedProductCommandFailure permits OfferedProductCommandFailure.Duplicate {
    String messageKey();

    record Duplicate() implements OfferedProductCommandFailure {
        @Override
        public String messageKey() {
            return "offeredProduct.error.duplicate";
        }
    }
}
