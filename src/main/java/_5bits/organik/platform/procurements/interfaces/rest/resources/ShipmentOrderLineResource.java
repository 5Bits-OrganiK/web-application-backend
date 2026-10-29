package _5bits.organik.platform.procurements.interfaces.rest.resources;

import java.time.LocalDate;

public record ShipmentOrderLineResource(
        Long id,
        String productId,
        Integer quantity,
        String lotCode,
        LocalDate expiresOn) {}
x