package _5bits.organik.platform.iam.interfaces.rest;

import _5bits.organik.platform.iam.application.commandservices.UserCommandService;
import _5bits.organik.platform.iam.application.queryservices.UserQueryService;
import _5bits.organik.platform.iam.domain.model.aggregates.User;
import _5bits.organik.platform.iam.domain.model.queries.GetAllUsersByMinimarketIdQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByEmailQuery;
import _5bits.organik.platform.iam.domain.model.queries.GetUserByIdQuery;
import _5bits.organik.platform.iam.interfaces.rest.resources.CreateUserResource;
import _5bits.organik.platform.iam.interfaces.rest.resources.UserResource;
import _5bits.organik.platform.iam.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import _5bits.organik.platform.iam.interfaces.rest.transform.IamValueObjectFromStringAssembler;
import _5bits.organik.platform.iam.interfaces.rest.transform.ResponseEntityFromUserCommandResultAssembler;
import _5bits.organik.platform.iam.interfaces.rest.transform.ResponseEntityFromUserQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Endpoints for users")
public class UsersController {
    private static final int MAX_QUERY_PARAM_LENGTH = 256;
    private static final Pattern ID_QUERY_PARAM_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final Pattern EMAIL_QUERY_PARAM_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final MessageSource messageSource;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService, MessageSource messageSource) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.messageSource = messageSource;
    }

    @Operation(
            summary = "Create a user",
            description = "Creates a user with the provided full name, email, role and business identifiers",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "User creation request",
                    content = @Content(schema = @Schema(implementation = CreateUserResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - user already exists for the given email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserResource resource) {
        log.debug("POST /api/v1/users – email={}, role={}", resource.email(), resource.role());
        var user = userCommandService
                .handle(CreateUserCommandFromResourceAssembler.toCommandFromResource(resource));
        var response = ResponseEntityFromUserCommandResultAssembler.toResponseEntityFromResult(user, messageSource);
        log.debug("POST /api/v1/users – response status={}", response.getStatusCode());
        return response;
    }

    @Operation(
            summary = "Get a user by ID",
            description = "Gets a user by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.debug("GET /api/v1/users/{}", id);
        Optional<User> user = userQueryService.handle(new GetUserByIdQuery(id));
        if (user.isEmpty()) {
            log.debug("User not found for id={}", id);
            return ResponseEntityFromUserQueryResultAssembler.notFound(
                    messageSource,
                    "user.error.notFoundById",
                    id);
        }
        return ResponseEntityFromUserQueryResultAssembler.toResponseEntityFromUser(user.orElseThrow());
    }

    private ResponseEntity<?> getAllUsersByMinimarketId(String minimarketId) {
        var getAllUsersByMinimarketIdQuery = new GetAllUsersByMinimarketIdQuery(
                IamValueObjectFromStringAssembler.toMinimarketIdFromString(minimarketId));
        var users = userQueryService.handle(getAllUsersByMinimarketIdQuery);
        return ResponseEntityFromUserQueryResultAssembler.toResponseEntityFromList(users);
    }

    private ResponseEntity<?> getUserByEmail(String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(
                IamValueObjectFromStringAssembler.toEmailAddressFromString(email));
        var user = userQueryService.handle(getUserByEmailQuery);
        if (user.isEmpty()) {
            log.debug("User not found for email={}", email);
            return ResponseEntityFromUserQueryResultAssembler.notFound(
                    messageSource,
                    "user.error.notFoundByEmail");
        }
        return ResponseEntityFromUserQueryResultAssembler.toResponseEntityFromUser(user.orElseThrow());
    }

    @Operation(
            summary = "Get users with parameters (Minimarket ID or Email)",
            description = "Gets the users of a minimarket, or a single user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User(s) found",
                    content = @Content(schema = @Schema(oneOf = {UserResource.class, UserResource[].class}))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<?> getUsersWithParameters(
            @Parameter(name = "minimarketId", description = "Minimarket ID",
                    schema = @Schema(maxLength = 256, pattern = "^[a-zA-Z0-9_-]+$"))
            @RequestParam(name = "minimarketId", required = false) String minimarketId,
            @Parameter(name = "email", description = "Email address",
                    schema = @Schema(maxLength = 256, pattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$"))
            @RequestParam(name = "email", required = false) String email) {
        if (minimarketId != null && isInvalidQueryParam(minimarketId, ID_QUERY_PARAM_PATTERN)) {
            log.warn("Rejected invalid query param minimarketId");
            return ResponseEntityFromUserQueryResultAssembler.badRequest(
                    messageSource,
                    "user.error.minimarketId.invalid");
        }
        if (email != null && isInvalidQueryParam(email, EMAIL_QUERY_PARAM_PATTERN)) {
            log.warn("Rejected invalid query param email");
            return ResponseEntityFromUserQueryResultAssembler.badRequest(
                    messageSource,
                    "user.error.email.invalid");
        }
        if (email != null && !email.isBlank()) {
            log.debug("GET /api/v1/users?email={}", email);
            return getUserByEmail(email);
        } else if (minimarketId != null && !minimarketId.isBlank()) {
            log.debug("GET /api/v1/users?minimarketId={}", minimarketId);
            return getAllUsersByMinimarketId(minimarketId);
        } else {
            log.warn("GET /api/v1/users – missing or blank required parameter: minimarketId or email");
            return ResponseEntityFromUserQueryResultAssembler.badRequest(
                    messageSource,
                    "user.error.query.missing");
        }
    }

    private boolean isInvalidQueryParam(String value, Pattern pattern) {
        return value.isBlank() || value.length() > MAX_QUERY_PARAM_LENGTH || !pattern.matcher(value).matches();
    }

}