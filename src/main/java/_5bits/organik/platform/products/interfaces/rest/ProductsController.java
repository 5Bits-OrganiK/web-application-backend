package _5bits.organik.platform.products.interfaces.rest;

import _5bits.organik.platform.products.application.commandservices.ProductCommandService;
import _5bits.organik.platform.products.application.queryservices.ProductQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsByCategoryIdQuery;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsQuery;
import _5bits.organik.platform.products.domain.model.queries.GetProductByIdQuery;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateProductResource;
import _5bits.organik.platform.products.interfaces.rest.resources.ProductResource;
import _5bits.organik.platform.products.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ProductsValueObjectFromStringAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ResponseEntityFromProductCommandResultAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ResponseEntityFromProductQueryResultAssembler;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Endpoints for products")
public class ProductsController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;
    private final MessageSource messageSource;

    public ProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService, MessageSource messageSource) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a product",
            description = "Creates a product with its SKU, name, category, supplier, measurement unit and minimum stock",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Product creation request",
                    content = @Content(schema = @Schema(implementation = CreateProductResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - product already exists for the given SKU",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductResource resource) {
        log.debug("POST /api/v1/products – sku={}, categoryId={}", resource.sku(), resource.categoryId());
        var product = productCommandService
                .handle(CreateProductCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromProductCommandResultAssembler.toResponseEntityFromResult(product, messageSource);
        log.debug("POST /api/v1/products – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Gets a product by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        log.debug("GET /api/v1/products/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromProductQueryResultAssembler.badRequest(
                    messageSource,
                    "product.error.id.invalid");
        }
        Optional<Product> product = productQueryService.handle(new GetProductByIdQuery(id));
        if (product.isEmpty()) {
            log.debug("Product not found for id={}", id);
            return ResponseEntityFromProductQueryResultAssembler.notFound(
                    messageSource,
                    "product.error.notFoundById",
                    id);
        }
        return ResponseEntityFromProductQueryResultAssembler.toResponseEntityFromProduct(product.orElseThrow());
    }

    @Operation(
            summary = "Get products with optional category filter",
            description = "Gets every registered product, or only the products of a category when categoryId is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = @Content(schema = @Schema(implementation = ProductResource[].class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getProductsWithParameters(
            @Parameter(name = "categoryId", description = "Category ID", schema = @Schema(minimum = "1"))
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        if (categoryId == null) {
            log.debug("GET /api/v1/products");
            var products = productQueryService.handle(new GetAllProductsQuery());
            return ResponseEntityFromProductQueryResultAssembler.toResponseEntityFromList(products);
        }
        if (categoryId <= 0) {
            log.warn("Rejected invalid query param categoryId");
            return ResponseEntityFromProductQueryResultAssembler.badRequest(
                    messageSource,
                    "product.error.categoryId.invalid");
        }
        log.debug("GET /api/v1/products?categoryId={}", categoryId);
        var products = productQueryService.handle(new GetAllProductsByCategoryIdQuery(
                ProductsValueObjectFromStringAssembler.toCategoryIdFromLong(categoryId)));
        return ResponseEntityFromProductQueryResultAssembler.toResponseEntityFromList(products);
    }
}
