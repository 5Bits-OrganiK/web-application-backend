package _5bits.organik.platform.procurements.domain.model.queries;

public record GetShipmentOrderByIdQuery(Long id) {
    public GetShipmentOrderByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Shipment order ID must be positive");
        }
    }
}
x