package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateStockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.StockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.CreateStockLotCommandFromResourceAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.InventoryValueObjectFromStringAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromStockLotCommandResultAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromStockLotQueryResultAssembler;
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

import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/stock-lots", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Stock Lots", description = "Endpoints for stock lots")
public class StockLotsController {
    private static final int MAX_QUERY_PARAM_LENGTH = 256;
    private static final Pattern ID_QUERY_PARAM_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    private final StockLotCommandService stockLotCommandService;
    private final StockLotQueryService stockLotQueryService;
    private final MessageSource messageSource;

    public StockLotsController(StockLotCommandService stockLotCommandService,
                               StockLotQueryService stockLotQueryService,
                               MessageSource messageSource) {
        this.stockLotCommandService = stockLotCommandService;
        this.stockLotQueryService = stockLotQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Register a stock lot",
            description = "Registers a stock lot of a product with its quantity, minimum stock and expiration date",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Stock lot registration request",
                    content = @Content(schema = @Schema(implementation = CreateStockLotResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock lot registered",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - lot code already registered for the minimarket",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createStockLot(@Valid @RequestBody CreateStockLotResource resource) {
        log.debug("POST /api/v1/stock-lots – minimarketId={}, lotCode={}", resource.minimarketId(), resource.lotCode());
        var result = stockLotCommandService
                .handle(CreateStockLotCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromStockLotCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
        log.debug("POST /api/v1/stock-lots – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get a stock lot by ID",
            description = "Gets a stock lot by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock lot found",
                    content = @Content(schema = @Schema(implementation = StockLotResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Stock lot not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getStockLotById(@PathVariable Long id) {
        log.debug("GET /api/v1/stock-lots/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromStockLotQueryResultAssembler.badRequest(messageSource, "stockLot.error.id.invalid");
        }
        var stockLot = stockLotQueryService.handle(new GetStockLotByIdQuery(id));
        if (stockLot.isEmpty()) {
            log.debug("Stock lot not found for id={}", id);
            return ResponseEntityFromStockLotQueryResultAssembler.notFound(messageSource, "stockLot.error.notFoundById", id);
        }
        return ResponseEntityFromStockLotQueryResultAssembler.toResponseEntityFromStockLot(stockLot.orElseThrow());
    }

    @Operation(
            summary = "Get the stock lots of a minimarket",
            description = "Gets all the stock lots registered by the provided minimarket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock lots found",
                    content = @Content(schema = @Schema(implementation = StockLotResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getStockLotsByMinimarketId(
            @Parameter(name = "minimarketId", description = "Minimarket ID",
                    schema = @Schema(maxLength = 256, pattern = "^[a-zA-Z0-9_-]+$"))
            @RequestParam(name = "minimarketId", required = false) String minimarketId) {
        if (minimarketId == null || minimarketId.isBlank()) {
            log.warn("GET /api/v1/stock-lots – missing or blank required parameter: minimarketId");
            return ResponseEntityFromStockLotQueryResultAssembler.badRequest(messageSource, "stockLot.error.query.missing");
        }
        if (isInvalidQueryParam(minimarketId)) {
            log.warn("Rejected invalid query param minimarketId");
            return ResponseEntityFromStockLotQueryResultAssembler.badRequest(messageSource, "inventory.error.minimarketId.invalid");
        }
        log.debug("GET /api/v1/stock-lots?minimarketId={}", minimarketId);
        var query = new GetAllStockLotsByMinimarketIdQuery(
                InventoryValueObjectFromStringAssembler.toMinimarketIdFromString(minimarketId));
        return ResponseEntityFromStockLotQueryResultAssembler.toResponseEntityFromList(stockLotQueryService.handle(query));
    }

    private boolean isInvalidQueryParam(String value) {
        return value.length() > MAX_QUERY_PARAM_LENGTH || !ID_QUERY_PARAM_PATTERN.matcher(value).matches();
    }
}
