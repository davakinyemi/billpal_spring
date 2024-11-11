package io.dav.billpal.controller;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.model.HttpResponse;
import io.dav.billpal.model.User;
import io.dav.billpal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST Controller for handling User-related operations
 * @author Dave AKN
 * @version 1.0
 */

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    // Dependency injection of UserService using constructor injection (facilitated by @RequiredArgsConstructor)
    private final UserService userService;

    /**
     * Handles user registration
     *
     * @param user The User object containing registration details
     * @return ResponseEntity with HttpResponse containing the created user details
     */
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user) {

        // Call the service layer to create the user and get the DTO
        UserDTO userDto = this.userService.createUser(user);

        // Construct and return the response
        return ResponseEntity.created(this.getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString()) // Current timestamp
                        .data(of("user", userDto)) // User data in a map
                        .message("User created") // Success message
                        .status(CREATED) // HTTP status
                        .statusCode(CREATED.value()) // HTTP status code
                        .build()
        );
    }

    /**
     * Generates a URI for the newly created user
     *
     * @return URI pointing to the endpoint to get the user details
     */
    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}

/**
 * Additional notes for overall understanding:
 * 1. This class, UserController, is a REST controller responsible for handling HTTP requests related to user operations, specifically user registration in this case.
 * 2. The @RestController annotation combines @Controller and @ResponseBody, indicating that this class defines RESTful web services.
 * 3. @RequestMapping(path = "/user") sets the base path for all endpoints in this controller to "/user".
 * 4. @RequiredArgsConstructor is a Lombok annotation that generates a constructor for all final fields, enabling constructor-based dependency injection.
 * 5. The saveUser method:
 *      - Is mapped to POST requests at "/user/register"
 *      - Accepts a User object in the request body, validated using @Valid
 *      - Returns a ResponseEntity<HttpResponse> with details of the created user
 * 6. The @Valid annotation ensures that the incoming User object is validated according to any validation annotations defined in the User class.
 * 7. The response includes:
 *      - A timestamp
 *      - The created user data (as a DTO)
 *      - A success message
 *      - HTTP status (CREATED - 201)
 * 8. The getUri() method generates a URI for the newly created user. Note that "<userId>" is a placeholder and should be replaced with the actual user ID in a real implementation.
 * 9. The use of ServletUriComponentsBuilder suggests that this application is running in a servlet environment (like a typical Spring Boot web application).
 * 10. The controller relies on a UserService for business logic, following the principle of separation of concerns.
 * 11. The use of DTOs (Data Transfer Objects) suggests a separation between internal domain objects and external API representations.
 * Remember to keep your controller methods focused on handling HTTP requests and responses, delegating business logic to the service layer. Also, consider adding error handling and
 * input validation to make your API more robust.
 */
