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
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * Converts a UserDTO (Data Transfer Object) to a User entity
     *
     * @param userDTO The UserDTO to be converted
     * @return A User entity containing the DTO's data
     */
    public static User toUser(final UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}