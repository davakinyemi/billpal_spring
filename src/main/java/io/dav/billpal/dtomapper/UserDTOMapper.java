package io.dav.billpal.dtomapper;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between User entities and UserDTO objects
 * @author Dave AKN
 * @version 1.0
 */
@Component
public class UserDTOMapper {

    /**
     * Converts a User entity to a UserDTO (Data Transfer Object)
     *
     * @param user The User entity to be converted
     * @return A UserDTO containing the user's data
     */
    public static UserDTO fromUser(final User user) {
        // Create a new UserDTO instance
        UserDTO userDTO = new UserDTO();

        // Copy properties from User to UserDTO
        // This uses Spring's BeanUtils to copy matching properties by name
        BeanUtils.copyProperties(user, userDTO);

        // Return the populated UserDTO
        return userDTO;
    }

    /**
     * Converts a UserDTO (Data Transfer Object) to a User entity
     *
     * @param userDTO The UserDTO to be converted
     * @return A User entity containing the DTO's data
     */
    public static User toUser(final UserDTO userDTO) {
        // Create a new User instance
        User user = new User();

        // Copy properties from UserDTO to User
        // This uses Spring's BeanUtils to copy matching properties by name
        BeanUtils.copyProperties(userDTO, user);

        // Return the populated User entity
        return user;
    }
}


/**
 * Additional notes for overall understanding:
 * 1. This class, UserDTOMapper, is responsible for mapping between User entities and UserDTO objects.
 * 2. The @Component annotation marks this class as a Spring-managed bean, allowing it to be automatically detected and registered in the application context.
 * 3. Both methods in this class are static, which means they can be called without creating an instance of UserDTOMapper.
 * 4. The fromUser method:
 *      - Converts a User entity to a UserDTO
 *      - This is typically used when sending user data to the client, to control what data is exposed
 * 5. The toUser method:
 *      - Converts a UserDTO to a User entity
 *      - This is typically used when receiving user data from the client, to prepare it for storage or processing
 * 6. Both methods use BeanUtils.copyProperties(), a utility method from Spring Framework:
 *      - It copies property values from one object to another
 *      - It matches properties by name
 *      - Only properties with matching names and compatible types will be copied
 * 7. This approach assumes that User and UserDTO have similar property names. If they differ, you might need to manually set some properties instead of using BeanUtils.copyProperties().
 * 8. The use of DTOs (Data Transfer Objects) helps to:
 *      - Control what data is exposed to the client
 *      - Decouple your internal domain model from your external API representation
 *      - Potentially improve performance by transferring only necessary data
 * 9. The final keyword in method parameters ensures that the passed objects cannot be reassigned within the method.
 * 10. This mapper doesn't handle any complex conversions or nested objects. If such conversions are needed, you might need to extend these methods or create additional mapping logic.
 * Remember that while this approach is simple and works well for straightforward mappings, for more complex scenarios or larger projects, you might want to consider using dedicated
 * mapping libraries like MapStruct or ModelMapper. These can provide more flexibility and reduce boilerplate code, especially when dealing with complex object structures or when you
 * need to frequently update your mapping logic.
 */