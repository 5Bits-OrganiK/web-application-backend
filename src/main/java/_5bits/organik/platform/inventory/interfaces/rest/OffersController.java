package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.OfferCommandService;
import _5bits.organik.platform.inventory.application.queryservices.OfferQueryService;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateOfferResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.OfferResource;
import _5bits.organik.platform.inventory.interfaces.rest.transform.CreateOfferCommandFromResourceAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromOfferCommandResultAssembler;
import _5bits.organik.platform.inventory.interfaces.rest.transform.ResponseEntityFromOfferQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/offers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Offers", description = "Endpoints for stock lot offers")
public class OffersController {
    private final OfferCommandService offerCommandService;
    private final OfferQueryService offerQueryService;
    private final MessageSource messageSource;

    public OffersController(OfferCommandService offerCommandService,
                            OfferQueryService offerQueryService,
                            MessageSource messageSource) {
        this.offerCommandService = offerCommandService;
        this.offerQueryService = offerQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create an offer",
            description = "Assigns a promotional price to a stock lot for a period that ends on or before its expiration date",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Offer creation request",
                    content = @Content(schema = @Schema(implementation = CreateOfferResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer created",
                    content = @Content(schema = @Schema(implementation = OfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Stock lot not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - offer ends after the lot expiration date",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createOffer(@Valid @RequestBody CreateOfferResource resource) {
        log.debug("POST /api/v1/offers – stockLotId={}, price={}", resource.stockLotId(), resource.price());
        var result = offerCommandService.handle(CreateOfferCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromOfferCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
        log.debug("POST /api/v1/offers – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get an offer by ID",
            description = "Gets an offer by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer found",
                    content = @Content(schema = @Schema(implementation = OfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Offer not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getOfferById(@PathVariable Long id) {
        log.debug("GET /api/v1/offers/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromOfferQueryResultAssembler.badRequest(messageSource, "offer.error.id.invalid");
        }
        var offer = offerQueryService.handle(new GetOfferByIdQuery(id));
        if (offer.isEmpty()) {
            log.debug("Offer not found for id={}", id);
            return ResponseEntityFromOfferQueryResultAssembler.notFound(messageSource, "offer.error.notFoundById", id);
        }
        return ResponseEntityFromOfferQueryResultAssembler.toResponseEntityFromOffer(offer.orElseThrow());
    }

    @Operation(
            summary = "Get the offers of a stock lot",
            description = "Gets all the offers of the provided stock lot, latest start date first")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offers found",
                    content = @Content(schema = @Schema(implementation = OfferResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getOffersByStockLotId(
            @Parameter(name = "stockLotId", description = "Stock lot ID", schema = @Schema(minimum = "1"))
            @RequestParam(name = "stockLotId", required = false) Long stockLotId) {
        if (stockLotId == null) {
            log.warn("GET /api/v1/offers – missing required parameter: stockLotId");
            return ResponseEntityFromOfferQueryResultAssembler.badRequest(messageSource, "offer.error.query.missing");
        }
        if (stockLotId <= 0) {
            log.warn("Rejected invalid query param stockLotId");
            return ResponseEntityFromOfferQueryResultAssembler.badRequest(messageSource, "inventory.error.stockLotId.invalid");
        }
        log.debug("GET /api/v1/offers?stockLotId={}", stockLotId);
        var query = new GetAllOffersByStockLotIdQuery(new StockLotId(stockLotId));
        return ResponseEntityFromOfferQueryResultAssembler.toResponseEntityFromList(offerQueryService.handle(query));
    }
}
