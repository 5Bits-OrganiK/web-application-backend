package _5bits.organik.platform.inventory.domain.model.queries;

public record GetStockLotByIdQuery(Long id) {
    public GetStockLotByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("stockLot.error.id.invalid");
    }
}
