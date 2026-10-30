package _5bits.organik.platform.requisition.domain.model.queries;

public record GetSupplyRequestByIdQuery(Long id) {
    public GetSupplyRequestByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("supplyRequest.error.id.invalid");
    }
}
