package _5bits.organik.platform.iam.application.commandservices;

public sealed interface UserCommandFailure permits UserCommandFailure.Duplicate {
    String messageKey();

    record Duplicate() implements UserCommandFailure {
       @Override
       public String messageKey() {
           return "user.error.duplicate";
       }
    }
}
