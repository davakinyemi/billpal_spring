package io.dav.billpal.service;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.model.User;

/**
 * @author Dave AKN
 * @version 1.0
 */
public interface UserService {
    UserDTO createUser(User user);
}
