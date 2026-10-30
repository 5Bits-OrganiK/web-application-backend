package _5bits.organik.platform.suppliers.interfaces.rest;

import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.application.queryservices.OfferedProductQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateOfferedProductResource;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.OfferedProductResource;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.CreateOfferedProductCommandFromResourceAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.ResponseEntityFromOfferedProductCommandResultAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.ResponseEntityFromOfferedProductQueryResultAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.SuppliersValueObjectFromStringAssembler;
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

import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/offered-products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Offered Products", description = "Endpoints for products offered by suppliers")
public class OfferedProductsController {
    private static final int MAX_QUERY_PARAM_LENGTH = 256;
    private static final Pattern ID_QUERY_PARAM_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    private final OfferedProductCommandService offeredProductCommandService;
    private final OfferedProductQueryService offeredProductQueryService;
    private final MessageSource messageSource;

    public OfferedProductsController(OfferedProductCommandService offeredProductCommandService, OfferedProductQueryService offeredProductQueryService, MessageSource messageSource) {
        this.offeredProductCommandService = offeredProductCommandService;
        this.offeredProductQueryService = offeredProductQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Register an offered product",
            description = "Registers a product lot offered by a supplier with its available quantity and expiration date",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Offered product creation request",
                    content = @Content(schema = @Schema(implementation = CreateOfferedProductResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offered product created",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - the supplier already registered this lot",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createOfferedProduct(@Valid @RequestBody CreateOfferedProductResource resource) {
        log.debug("POST /api/v1/offered-products – supplierId={}, lotCode={}", resource.supplierId(), resource.lotCode());
        var offeredProduct = offeredProductCommandService
                .handle(CreateOfferedProductCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromOfferedProductCommandResultAssembler.toResponseEntityFromResult(offeredProduct, messageSource);
        log.debug("POST /api/v1/offered-products – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get an offered product by ID",
            description = "Gets an offered product by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offered product found",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Offered product not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getOfferedProductById(@PathVariable Long id) {
        log.debug("GET /api/v1/offered-products/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromOfferedProductQueryResultAssembler.badRequest(
                    messageSource,
                    "offeredProduct.error.id.invalid");
        }
        Optional<OfferedProduct> offeredProduct = offeredProductQueryService.handle(new GetOfferedProductByIdQuery(id));
        if (offeredProduct.isEmpty()) {
            log.debug("Offered product not found for id={}", id);
            return ResponseEntityFromOfferedProductQueryResultAssembler.notFound(
                    messageSource,
                    "offeredProduct.error.notFoundById",
                    id);
        }
        return ResponseEntityFromOfferedProductQueryResultAssembler.toResponseEntityFromOfferedProduct(offeredProduct.orElseThrow());
    }

    @Operation(
            summary = "Get the offered products of a supplier",
            description = "Gets every product lot offered by the provided supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offered products found",
                    content = @Content(schema = @Schema(implementation = OfferedProductResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getOfferedProductsBySupplierId(
            @Parameter(name = "supplierId", description = "Supplier ID", required = true,
                    schema = @Schema(maxLength = 256, pattern = "^[a-zA-Z0-9_-]+$"))
            @RequestParam(name = "supplierId", required = false) String supplierId) {
        if (supplierId == null || supplierId.isBlank()) {
            log.warn("GET /api/v1/offered-products – missing or blank required parameter: supplierId");
            return ResponseEntityFromOfferedProductQueryResultAssembler.badRequest(
                    messageSource,
                    "offeredProduct.error.query.missing");
        }
        if (isInvalidQueryParam(supplierId)) {
            log.warn("Rejected invalid query param supplierId");
            return ResponseEntityFromOfferedProductQueryResultAssembler.badRequest(
                    messageSource,
                    "offeredProduct.error.supplierId.invalid");
        }
        log.debug("GET /api/v1/offered-products?supplierId={}", supplierId);
        var offeredProducts = offeredProductQueryService.handle(new GetAllOfferedProductsBySupplierIdQuery(
                SuppliersValueObjectFromStringAssembler.toSupplierIdFromString(supplierId)));
        return ResponseEntityFromOfferedProductQueryResultAssembler.toResponseEntityFromList(offeredProducts);
    }

    private boolean isInvalidQueryParam(String value) {
        return value.length() > MAX_QUERY_PARAM_LENGTH || !ID_QUERY_PARAM_PATTERN.matcher(value).matches();
    }
}
