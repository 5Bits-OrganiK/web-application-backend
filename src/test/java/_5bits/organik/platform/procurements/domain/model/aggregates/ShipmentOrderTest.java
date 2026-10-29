package _5bits.organik.platform.procurements.domain.model.aggregates;

import _5bits.organik.platform.procurements.domain.model.commands.CreateShipmentOrderCommand;
import _5bits.organik.platform.procurements.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.OrderStatus;
import _5bits.organik.platform.procurements.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShipmentOrderTest {
    @Test
    void createsPendingOrderWithAllLines() {
        var expiresOn = LocalDate.of(2026, 12, 31);
        var command = new CreateShipmentOrderCommand(
                new SupplierId("sup-1"),
                new MinimarketId("mm-1"),
                List.of(new CreateShipmentOrderCommand.Line(
                        new ProductId("prod-1"), new Quantity(5), new LotCode("lot-1"), expiresOn)));

        var order = new ShipmentOrder(command);

        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("sup-1", order.getSupplierId().value());
        assertEquals("mm-1", order.getMinimarketId().value());
        assertEquals(1, order.getLines().size());
        assertEquals("prod-1", order.getLines().getFirst().getProductId().value());
        assertEquals(5, order.getLines().getFirst().getQuantity().value());
        assertEquals(expiresOn, order.getLines().getFirst().getExpiresOn());
        assertNull(order.getDecidedAt());
        assertNull(order.getDecidedBy());
    }
}
