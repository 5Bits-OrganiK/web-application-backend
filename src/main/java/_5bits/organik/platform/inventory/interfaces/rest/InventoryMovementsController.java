package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandService;
import _5bits.organik.platform.inventory.application.queryservices.InventoryMovementQueryService;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.InventoryMovementResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.RegisterInventoryMovementResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.RegisterInventoryMovementCommandFromResourceAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromInventoryMovementCommandResultAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromInventoryMovementQueryResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/inventory-movements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Inventory Movements", description = "Endpoints for inventory movements")
public class InventoryMovementsController {
    private final InventoryMovementCommandService inventoryMovementCommandService;
    private final InventoryMovementQueryService inventoryMovementQueryService;
    private final MessageSource messageSource;

    public InventoryMovementsController(InventoryMovementCommandService inventoryMovementCommandService,
                                        InventoryMovementQueryService inventoryMovementQueryService,
                                        MessageSource messageSource) {
        this.inventoryMovementCommandService = inventoryMovementCommandService;
        this.inventoryMovementQueryService = inventoryMovementQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Register an inventory movement",
            description = "Registers an entry, exit, adjustment or waste on a stock lot",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Inventory movement registration request",
                    content = @Content(schema = @Schema(implementation = RegisterInventoryMovementResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory movement registered",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Stock lot not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - not enough stock in the lot",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> registerInventoryMovement(@Valid @RequestBody RegisterInventoryMovementResource resource) {
        log.debug("POST /api/v1/inventory-movements – stockLotId={}, type={}", resource.stockLotId(), resource.type());
        var result = inventoryMovementCommandService
                .handle(RegisterInventoryMovementCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromInventoryMovementCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
        log.debug("POST /api/v1/inventory-movements – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get an inventory movement by ID",
            description = "Gets an inventory movement by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory movement found",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Inventory movement not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getInventoryMovementById(@PathVariable Long id) {
        log.debug("GET /api/v1/inventory-movements/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromInventoryMovementQueryResultAssembler.badRequest(
                    messageSource, "inventoryMovement.error.id.invalid");
        }
        var movement = inventoryMovementQueryService.handle(new GetInventoryMovementByIdQuery(id));
        if (movement.isEmpty()) {
            log.debug("Inventory movement not found for id={}", id);
            return ResponseEntityFromInventoryMovementQueryResultAssembler.notFound(
                    messageSource, "inventoryMovement.error.notFoundById", id);
        }
        return ResponseEntityFromInventoryMovementQueryResultAssembler.toResponseEntityFromInventoryMovement(movement.orElseThrow());
    }

    @Operation(
            summary = "Get the movements of a stock lot",
            description = "Gets all the inventory movements of the provided stock lot, newest first")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory movements found",
                    content = @Content(schema = @Schema(implementation = InventoryMovementResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getInventoryMovementsByStockLotId(
            @Parameter(name = "stockLotId", description = "Stock lot ID", schema = @Schema(minimum = "1"))
            @RequestParam(name = "stockLotId", required = false) Long stockLotId) {
        if (stockLotId == null) {
            log.warn("GET /api/v1/inventory-movements – missing required parameter: stockLotId");
            return ResponseEntityFromInventoryMovementQueryResultAssembler.badRequest(
                    messageSource, "inventoryMovement.error.query.missing");
        }
        if (stockLotId <= 0) {
            log.warn("Rejected invalid query param stockLotId");
            return ResponseEntityFromInventoryMovementQueryResultAssembler.badRequest(
                    messageSource, "inventory.error.stockLotId.invalid");
        }
        log.debug("GET /api/v1/inventory-movements?stockLotId={}", stockLotId);
        var query = new GetAllInventoryMovementsByStockLotIdQuery(new StockLotId(stockLotId));
        return ResponseEntityFromInventoryMovementQueryResultAssembler.toResponseEntityFromList(
                inventoryMovementQueryService.handle(query));
    }
}
