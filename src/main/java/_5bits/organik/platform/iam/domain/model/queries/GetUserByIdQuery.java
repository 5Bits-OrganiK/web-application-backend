package _5bits.organik.platform.iam.domain.model.queries;

public record GetUserByIdQuery(Long id) {
    public GetUserByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("user.error.id.invalid");
    }
}
