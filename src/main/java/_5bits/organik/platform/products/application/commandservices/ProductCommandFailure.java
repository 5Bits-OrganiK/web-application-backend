package _5bits.organik.platform.products.application.commandservices;

public sealed interface ProductCommandFailure permits ProductCommandFailure.Duplicate, ProductCommandFailure.CategoryNotFound {
    String messageKey();

    record Duplicate() implements ProductCommandFailure {
        @Override
        public String messageKey() {
            return "product.error.duplicate";
        }
    }

    record CategoryNotFound() implements ProductCommandFailure {
        @Override
        public String messageKey() {
            return "product.error.categoryNotFound";
        }
    }
}
