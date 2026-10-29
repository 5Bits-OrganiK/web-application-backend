package _5bits.organik.platform.procurements.interfaces.rest;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.application.queryservices.ShipmentOrderQueryService;
import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.interfaces.rest.resources.CreateShipmentOrderLineResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.CreateShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShipmentOrdersControllerTest {
    private final ShipmentOrderCommandService commandService = mock(ShipmentOrderCommandService.class);
    private final ShipmentOrderQueryService queryService = mock(ShipmentOrderQueryService.class);
    private final StaticMessageSource messages = new StaticMessageSource();
    private final ShipmentOrdersController controller = new ShipmentOrdersController(commandService, queryService, messages);

    @Test
    void createsOrderAndReturnsItsResource() {
        var request = new CreateShipmentOrderResource("sup-1", "mm-1", List.of(
                new CreateShipmentOrderLineResource("prod-1", 3, "lot-1", LocalDate.of(2026, 12, 31))));
        when(commandService.handle(any())).thenAnswer(invocation ->
                new ShipmentOrder(invocation.getArgument(0)));

        var response = controller.createShipmentOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        var body = assertInstanceOf(ShipmentOrderResource.class, response.getBody());
        assertEquals("PENDING", body.status());
        assertEquals(3, body.lines().getFirst().quantity());
        verify(commandService).handle(any());
    }

    @Test
    void returnsNotFoundForUnknownOrder() {
        messages.addMessage("shipmentOrder.error.notFoundById", LocaleContextHolder.getLocale(), "Order {0} not found");
        when(queryService.handle(new GetShipmentOrderByIdQuery(42L))).thenReturn(Optional.empty());

        var response = controller.getShipmentOrderById(42L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order 42 not found", assertInstanceOf(ProblemDetail.class, response.getBody()).getDetail());
    }

    @Test
    void rejectsMissingAndAmbiguousFilters() {
        var missing = controller.getShipmentOrders(null, null);
        var ambiguous = controller.getShipmentOrders("sup-1", "mm-1");

        assertEquals(HttpStatus.BAD_REQUEST, missing.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, ambiguous.getStatusCode());
    }

    @Test
    void listsOrdersForSupplier() {
        when(queryService.handle(new GetShipmentOrdersBySupplierIdQuery(new SupplierId("sup-1"))))
                .thenReturn(List.of());

        var response = controller.getShipmentOrders("sup-1", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(), response.getBody());
        verify(queryService).handle(new GetShipmentOrdersBySupplierIdQuery(new SupplierId("sup-1")));
    }

    @Test
    void validatesNestedOrderLines() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var request = new CreateShipmentOrderResource("sup-1", "mm-1", List.of(
                    new CreateShipmentOrderLineResource("prod-1", 0, "lot-1", LocalDate.of(2026, 12, 31))));

            var violations = factory.getValidator().validate(request);

            assertEquals(1, violations.size());
            assertEquals("lines[0].quantity", violations.iterator().next().getPropertyPath().toString());
        }
    }
}
x