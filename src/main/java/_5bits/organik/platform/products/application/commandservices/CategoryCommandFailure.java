package _5bits.organik.platform.products.application.commandservices;

public sealed interface CategoryCommandFailure permits CategoryCommandFailure.Duplicate {
    String messageKey();

    record Duplicate() implements CategoryCommandFailure {
        @Override
        public String messageKey() {
            return "category.error.duplicate";
        }
    }
}
