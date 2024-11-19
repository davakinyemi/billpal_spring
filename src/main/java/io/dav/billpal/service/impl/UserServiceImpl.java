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

    private final UserRepository<User> userRepository;

    /**
     * Creates a new user and returns the user data as a DTO
     *
     * @param user The User object containing the data for the new user
     * @return UserDTO representing the newly created user
     */
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(this.userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTOMapper.fromUser(this.userRepository.getUserByEmail(email));
    }
}