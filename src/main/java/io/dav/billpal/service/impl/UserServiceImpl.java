package io.dav.billpal.service.impl;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.dtomapper.UserDTOMapper;
import io.dav.billpal.model.User;
import io.dav.billpal.repository.UserRepository;
import io.dav.billpal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService interface
 * This service class handles business logic related to User entities
 * @author Dave AKN
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Dependency injection of UserRepository
    // The use of generics (<User>) suggests that UserRepository is likely an interface
    // implemented by multiple repository classes for different entity types
    private final UserRepository<User> userRepository;

    /**
     * Creates a new user and returns the user data as a DTO
     *
     * @param user The User object containing the data for the new user
     * @return UserDTO representing the newly created user
     */
    @Override
    public UserDTO createUser(User user) {
        // 1. Call the repository to create the user in the database
        // 2. Convert the returned User entity to a UserDTO
        // 3. Return the UserDTO
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}


/**
 * Additional notes for overall understanding:
 * 1. This UserServiceImpl class implements the UserService interface, providing the actual implementation of the service methods.
 * 2. The @Service annotation marks this class as a service component in Spring's component scanning. This allows Spring to automatically detect and register this bean in the application context.
 * 3. @RequiredArgsConstructor is a Lombok annotation that generates a constructor for all final fields. This enables constructor-based dependency injection for the userRepository.
 * 4. The class depends on UserRepository<User>, which is likely an interface defining data access methods for User entities. The use of generics suggests that there might be multiple implementations of UserRepository for different entity types.
 * 5. The createUser method:
 *      - Takes a User object as input, which contains the data for the new user to be created.
 *      - Calls the create method of the userRepository to persist the new user in the database.
 *      - Uses UserDTOMapper.fromUser() to convert the returned User entity to a UserDTO.
 *      - Returns the UserDTO representing the newly created user.
 * 6. The use of DTOs (Data Transfer Objects) suggests a separation between the internal domain model (User) and the external API representation (UserDTO).
 * 7. This implementation only includes the createUser method. If the UserService interface defines other methods, they would need to be implemented here as well.
 * 8. The service layer acts as an intermediary between the controller and the repository, allowing for the addition of business logic, validation, or other operations before or after the data access occurs.
 * 9. There's no explicit transaction management visible in this code. Depending on your application's needs, you might want to add @Transactional annotations to manage database transactions.
 * 10. Error handling is not visible in this snippet. In a more complete implementation, you might want to add try-catch blocks or use Spring's exception handling mechanisms to manage potential errors during user creation.
 * This service implementation provides a clean separation of concerns, handling the business logic of user creation while delegating data access to the repository layer and using DTOs for data transfer to the presentation layer.
 */