package _5bits.organik.platform.procurements.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record ShipmentOrderResource(
        Long id,
        String supplierId,
        String minimarketId,
        List<ShipmentOrderLineResource> lines,
        String status,
        Long decidedBy,
        Instant decidedAt,
        String rejectionReason,
        Instant createdAt,
        Instant updatedAt) {}
x