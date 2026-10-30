package _5bits.organik.platform.suppliers.application.commandservices;

public sealed interface SupplierCommandFailure permits SupplierCommandFailure.Duplicate {
    String messageKey();

    record Duplicate() implements SupplierCommandFailure {
        @Override
        public String messageKey() {
            return "supplier.error.duplicate";
        }
    }
}
