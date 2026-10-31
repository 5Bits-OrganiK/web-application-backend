package _5bits.organik.platform.products.interfaces.rest;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandService;
import _5bits.organik.platform.products.application.queryservices.CategoryQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.queries.GetAllCategoriesQuery;
import _5bits.organik.platform.products.domain.model.queries.GetCategoryByIdQuery;
import _5bits.organik.platform.products.interfaces.rest.resources.CategoryResource;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateCategoryResource;
import _5bits.organik.platform.products.interfaces.rest.transform.CreateCategoryCommandFromResourceAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ResponseEntityFromCategoryCommandResultAssembler;
import _5bits.organik.platform.products.interfaces.rest.transform.ResponseEntityFromCategoryQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Endpoints for product categories")
public class CategoriesController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;
    private final MessageSource messageSource;

    public CategoriesController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService, MessageSource messageSource) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a category",
            description = "Creates a product category with the provided name and accent color",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Category creation request",
                    content = @Content(schema = @Schema(implementation = CreateCategoryResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created",
                    content = @Content(schema = @Schema(implementation = CategoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - category already exists for the given name",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryResource resource) {
        log.debug("POST /api/v1/categories – name={}, tone={}", resource.name(), resource.tone());
        var category = categoryCommandService
                .handle(CreateCategoryCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromCategoryCommandResultAssembler.toResponseEntityFromResult(category, messageSource);
        log.debug("POST /api/v1/categories – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get a category by ID",
            description = "Gets a product category by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        log.debug("GET /api/v1/categories/{}", id);
        if (id == null || id <= 0) {
            return ResponseEntityFromCategoryQueryResultAssembler.badRequest(
                    messageSource,
                    "category.error.id.invalid");
        }
        Optional<Category> category = categoryQueryService.handle(new GetCategoryByIdQuery(id));
        if (category.isEmpty()) {
            log.debug("Category not found for id={}", id);
            return ResponseEntityFromCategoryQueryResultAssembler.notFound(
                    messageSource,
                    "category.error.notFoundById",
                    id);
        }
        return ResponseEntityFromCategoryQueryResultAssembler.toResponseEntityFromCategory(category.orElseThrow());
    }

    @Operation(
            summary = "Get all categories",
            description = "Gets every registered product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found",
                    content = @Content(schema = @Schema(implementation = CategoryResource[].class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        log.debug("GET /api/v1/categories");
        var categories = categoryQueryService.handle(new GetAllCategoriesQuery());
        return ResponseEntityFromCategoryQueryResultAssembler.toResponseEntityFromList(categories);
    }
}
