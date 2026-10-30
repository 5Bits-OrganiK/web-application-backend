package _5bits.organik.platform.procurements.interfaces.rest;

import _5bits.organik.platform.procurements.application.commandservices.ShipmentOrderCommandService;
import _5bits.organik.platform.procurements.application.queryservices.ShipmentOrderQueryService;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;
import _5bits.organik.platform.procurements.interfaces.rest.resources.CreateShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.AcceptShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.resources.RejectShipmentOrderResource;
import _5bits.organik.platform.procurements.interfaces.rest.transform.CreateShipmentOrderCommandFromResourceAssembler;
import _5bits.organik.platform.procurements.interfaces.rest.transform.ResponseEntityFromShipmentOrderCommandResultAssembler;
import _5bits.organik.platform.procurements.interfaces.rest.transform.ResponseEntityFromShipmentOrderQueryResultAssembler;
import _5bits.organik.platform.procurements.interfaces.rest.transform.ShipmentOrderDecisionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/shipment-orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Shipment Orders", description = "Create, query and decide shipment orders")
public class ShipmentOrdersController {
    private static final int MAX_QUERY_PARAM_LENGTH = 256;
    private static final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    private final ShipmentOrderCommandService commandService;
    private final ShipmentOrderQueryService queryService;
    private final MessageSource messageSource;

    public ShipmentOrdersController(
            ShipmentOrderCommandService commandService,
            ShipmentOrderQueryService queryService,
            MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a shipment order")
    @PostMapping
    public ResponseEntity<?> createShipmentOrder(@Valid @RequestBody CreateShipmentOrderResource resource) {
        var command = CreateShipmentOrderCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityFromShipmentOrderCommandResultAssembler.toResponseEntityFromResult(
                result, messageSource, HttpStatus.CREATED);
    }

    @Operation(summary = "Accept a pending shipment order")
    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptShipmentOrder(
            @PathVariable Long id, @Valid @RequestBody AcceptShipmentOrderResource resource) {
        if (id == null || id <= 0) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.id.invalid");
        }
        var command = ShipmentOrderDecisionCommandFromResourceAssembler.toAcceptCommand(id, resource);
        return ResponseEntityFromShipmentOrderCommandResultAssembler.toResponseEntityFromResult(
                commandService.handle(command), messageSource, HttpStatus.OK);
    }

    @Operation(summary = "Reject a pending shipment order")
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectShipmentOrder(
            @PathVariable Long id, @Valid @RequestBody RejectShipmentOrderResource resource) {
        if (id == null || id <= 0) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.id.invalid");
        }
        var command = ShipmentOrderDecisionCommandFromResourceAssembler.toRejectCommand(id, resource);
        return ResponseEntityFromShipmentOrderCommandResultAssembler.toResponseEntityFromResult(
                commandService.handle(command), messageSource, HttpStatus.OK);
    }

    @Operation(summary = "Get a shipment order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getShipmentOrderById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.id.invalid");
        }
        return queryService.handle(new GetShipmentOrderByIdQuery(id))
                .<ResponseEntity<?>>map(ResponseEntityFromShipmentOrderQueryResultAssembler::ok)
                .orElseGet(() -> ResponseEntityFromShipmentOrderQueryResultAssembler.notFound(
                        messageSource, "shipmentOrder.error.notFoundById", id));
    }

    @Operation(summary = "Get shipment orders by supplier or minimarket ID")
    @GetMapping
    public ResponseEntity<?> getShipmentOrders(
            @RequestParam(name = "supplierId", required = false) String supplierId,
            @RequestParam(name = "minimarketId", required = false) String minimarketId) {
        if (supplierId != null && invalidId(supplierId)) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.supplierId.invalid");
        }
        if (minimarketId != null && invalidId(minimarketId)) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.minimarketId.invalid");
        }
        if (supplierId != null && minimarketId != null) {
            return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                    messageSource, "shipmentOrder.error.query.ambiguous");
        }
        if (supplierId != null) {
            var orders = queryService.handle(new GetShipmentOrdersBySupplierIdQuery(new SupplierId(supplierId)));
            return ResponseEntityFromShipmentOrderQueryResultAssembler.ok(orders);
        }
        if (minimarketId != null) {
            var orders = queryService.handle(new GetShipmentOrdersByMinimarketIdQuery(new MinimarketId(minimarketId)));
            return ResponseEntityFromShipmentOrderQueryResultAssembler.ok(orders);
        }
        return ResponseEntityFromShipmentOrderQueryResultAssembler.badRequest(
                messageSource, "shipmentOrder.error.query.missing");
    }

    private boolean invalidId(String value) {
        return value.isBlank() || value.length() > MAX_QUERY_PARAM_LENGTH || !ID_PATTERN.matcher(value).matches();
    }
}
