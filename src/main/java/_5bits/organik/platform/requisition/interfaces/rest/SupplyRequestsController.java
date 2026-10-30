package _5bits.organik.platform.requisition.interfaces.rest;

import _5bits.organik.platform.requisition.application.commandservices.SupplyRequestCommandService;
import _5bits.organik.platform.requisition.application.queryservices.SupplyRequestQueryService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;
import _5bits.organik.platform.requisition.interfaces.rest.resources.CreateSupplyRequestResource;
import _5bits.organik.platform.requisition.interfaces.rest.resources.SupplyRequestResource;
import _5bits.organik.platform.requisition.interfaces.rest.transform.CreateSupplyRequestCommandFromResourceAssembler;
import _5bits.organik.platform.requisition.interfaces.rest.transform.RequisitionValueObjectFromStringAssembler;
import _5bits.organik.platform.requisition.interfaces.rest.transform.ResponseEntityFromSupplyRequestCommandResultAssembler;
import _5bits.organik.platform.requisition.interfaces.rest.transform.ResponseEntityFromSupplyRequestQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/supply-requests", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Supply Requests", description = "Endpoints for supply requests")
public class SupplyRequestsController {
    private static final int MAX_QUERY_PARAM_LENGTH = 256;
    private static final Pattern ID_QUERY_PARAM_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    private final SupplyRequestCommandService supplyRequestCommandService;
    private final SupplyRequestQueryService supplyRequestQueryService;
    private final MessageSource messageSource;

    public SupplyRequestsController(SupplyRequestCommandService supplyRequestCommandService, SupplyRequestQueryService supplyRequestQueryService, MessageSource messageSource) {
        this.supplyRequestCommandService = supplyRequestCommandService;
        this.supplyRequestQueryService = supplyRequestQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a supply request",
            description = "Creates a pending supply request from a minimarket to a supplier",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Supply request creation request",
                    content = @Content(schema = @Schema(implementation = CreateSupplyRequestResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supply request created",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createSupplyRequest(@Valid @RequestBody CreateSupplyRequestResource resource) {
        log.debug("POST /api/v1/supply-requests – minimarketId={}, supplierId={}", resource.minimarketId(), resource.supplierId());
        var supplyRequest = supplyRequestCommandService
                .handle(CreateSupplyRequestCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromSupplyRequestCommandResultAssembler.toResponseEntityFromSupplyRequest(supplyRequest);
    }

    @Operation(
            summary = "Get a supply request by ID",
            description = "Gets a supply request by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply request found",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Supply request not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getSupplyRequestById(@PathVariable Long id) {
        log.debug("GET /api/v1/supply-requests/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromSupplyRequestQueryResultAssembler.badRequest(
                    messageSource,
                    "supplyRequest.error.id.invalid");
        }
        Optional<SupplyRequest> supplyRequest = supplyRequestQueryService.handle(new GetSupplyRequestByIdQuery(id));
        if (supplyRequest.isEmpty()) {
            log.debug("Supply request not found for id={}", id);
            return ResponseEntityFromSupplyRequestQueryResultAssembler.notFound(
                    messageSource,
                    "supplyRequest.error.notFoundById",
                    id);
        }
        return ResponseEntityFromSupplyRequestQueryResultAssembler.toResponseEntityFromSupplyRequest(supplyRequest.orElseThrow());
    }

    @Operation(
            summary = "Get supply requests by minimarket or supplier ID",
            description = "Gets the supply requests of a minimarket, or the ones received by a supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supply requests found",
                    content = @Content(schema = @Schema(implementation = SupplyRequestResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getSupplyRequestsWithParameters(
            @Parameter(name = "minimarketId", description = "Minimarket ID",
                    schema = @Schema(maxLength = 256, pattern = "^[a-zA-Z0-9_-]+$"))
            @RequestParam(name = "minimarketId", required = false) String minimarketId,
            @Parameter(name = "supplierId", description = "Supplier ID",
                    schema = @Schema(maxLength = 256, pattern = "^[a-zA-Z0-9_-]+$"))
            @RequestParam(name = "supplierId", required = false) String supplierId) {
        if (minimarketId != null && isInvalidQueryParam(minimarketId)) {
            log.warn("Rejected invalid query param minimarketId");
            return ResponseEntityFromSupplyRequestQueryResultAssembler.badRequest(
                    messageSource,
                    "supplyRequest.error.minimarketId.invalid");
        }
        if (supplierId != null && isInvalidQueryParam(supplierId)) {
            log.warn("Rejected invalid query param supplierId");
            return ResponseEntityFromSupplyRequestQueryResultAssembler.badRequest(
                    messageSource,
                    "supplyRequest.error.supplierId.invalid");
        }
        if (minimarketId != null && supplierId != null) {
            return ResponseEntityFromSupplyRequestQueryResultAssembler.badRequest(
                    messageSource,
                    "supplyRequest.error.query.ambiguous");
        }
        if (minimarketId != null) {
            log.debug("GET /api/v1/supply-requests?minimarketId={}", minimarketId);
            var supplyRequests = supplyRequestQueryService.handle(new GetAllSupplyRequestsByMinimarketIdQuery(
                    RequisitionValueObjectFromStringAssembler.toMinimarketIdFromString(minimarketId)));
            return ResponseEntityFromSupplyRequestQueryResultAssembler.toResponseEntityFromList(supplyRequests);
        }
        if (supplierId != null) {
            log.debug("GET /api/v1/supply-requests?supplierId={}", supplierId);
            var supplyRequests = supplyRequestQueryService.handle(new GetAllSupplyRequestsBySupplierIdQuery(
                    RequisitionValueObjectFromStringAssembler.toSupplierIdFromString(supplierId)));
            return ResponseEntityFromSupplyRequestQueryResultAssembler.toResponseEntityFromList(supplyRequests);
        }
        log.warn("GET /api/v1/supply-requests – missing required parameter: minimarketId or supplierId");
        return ResponseEntityFromSupplyRequestQueryResultAssembler.badRequest(
                messageSource,
                "supplyRequest.error.query.missing");
    }

    private boolean isInvalidQueryParam(String value) {
        return value.isBlank() || value.length() > MAX_QUERY_PARAM_LENGTH || !ID_QUERY_PARAM_PATTERN.matcher(value).matches();
    }
}
