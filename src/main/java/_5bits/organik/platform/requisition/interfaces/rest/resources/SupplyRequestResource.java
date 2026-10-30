package _5bits.organik.platform.requisition.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a supply request")
public record SupplyRequestResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Product to restock", example = "prod-quinua-blanca")
        String productId,
        @Schema(description = "Supplier asked for the restock", example = "1")
        String supplierId,
        @Schema(description = "Minimarket that needs the restock", example = "mm-vida-verde")
        String minimarketId,
        @Schema(description = "Requested quantity", example = "30")
        Integer quantity,
        @Schema(description = "Reason of the restock", example = "Demanda semanal")
        String reason,
        @Schema(description = "Date the products are required", example = "2026-11-10")
        LocalDate requiredDate,
        @Schema(description = "Priority of the request", example = "HIGH")
        String priority,
        @Schema(description = "Status of the request", example = "PENDING")
        String status) {
}
