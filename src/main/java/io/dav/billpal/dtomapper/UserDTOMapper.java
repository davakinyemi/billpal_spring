package io.dav.billpal.dtomapper;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Component
public class UserDTOMapper {
    public static UserDTO fromUser(final User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User toUser(final UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
