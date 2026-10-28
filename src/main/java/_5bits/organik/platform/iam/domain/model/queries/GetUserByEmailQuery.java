package _5bits.organik.platform.iam.domain.model.queries;

import _5bits.organik.platform.iam.domain.model.valueobjects.EmailAddress;

public record GetUserByEmailQuery(EmailAddress email) {
    public GetUserByEmailQuery {
        if (email == null) throw new IllegalArgumentException("email cannot be null");
    }
}
