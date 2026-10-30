package _5bits.organik.platform.suppliers.interfaces.rest;

import _5bits.organik.platform.suppliers.application.commandservices.SupplierCommandService;
import _5bits.organik.platform.suppliers.application.queryservices.SupplierQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateSupplierResource;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.SupplierResource;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.CreateSupplierCommandFromResourceAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.ResponseEntityFromSupplierCommandResultAssembler;
import _5bits.organik.platform.suppliers.interfaces.rest.transform.ResponseEntityFromSupplierQueryResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/suppliers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Suppliers", description = "Endpoints for suppliers")
public class SuppliersController {
    private final SupplierCommandService supplierCommandService;
    private final SupplierQueryService supplierQueryService;
    private final MessageSource messageSource;

    public SuppliersController(SupplierCommandService supplierCommandService, SupplierQueryService supplierQueryService, MessageSource messageSource) {
        this.supplierCommandService = supplierCommandService;
        this.supplierQueryService = supplierQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a supplier",
            description = "Creates a supplier with its contact data, categories and organic certification",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Supplier creation request",
                    content = @Content(schema = @Schema(implementation = CreateSupplierResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - supplier already exists for the given email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody CreateSupplierResource resource) {
        log.debug("POST /api/v1/suppliers – email={}", resource.email());
        var supplier = supplierCommandService
                .handle(CreateSupplierCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromSupplierCommandResultAssembler.toResponseEntityFromResult(supplier, messageSource);
        log.debug("POST /api/v1/suppliers – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get a supplier by ID",
            description = "Gets a supplier by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier found",
                    content = @Content(schema = @Schema(implementation = SupplierResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Long id) {
        log.debug("GET /api/v1/suppliers/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromSupplierQueryResultAssembler.badRequest(
                    messageSource,
                    "supplier.error.id.invalid");
        }
        Optional<Supplier> supplier = supplierQueryService.handle(new GetSupplierByIdQuery(id));
        if (supplier.isEmpty()) {
            log.debug("Supplier not found for id={}", id);
            return ResponseEntityFromSupplierQueryResultAssembler.notFound(
                    messageSource,
                    "supplier.error.notFoundById",
                    id);
        }
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromSupplier(supplier.orElseThrow());
    }

    @Operation(
            summary = "Get all suppliers",
            description = "Gets every registered supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suppliers found",
                    content = @Content(schema = @Schema(implementation = SupplierResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        log.debug("GET /api/v1/suppliers");
        var suppliers = supplierQueryService.handle(new GetAllSuppliersQuery());
        return ResponseEntityFromSupplierQueryResultAssembler.toResponseEntityFromList(suppliers);
    }
}
