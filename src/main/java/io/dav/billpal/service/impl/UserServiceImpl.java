package io.dav.billpal.service.impl;

import io.dav.billpal.dto.UserDTO;
import io.dav.billpal.dtomapper.UserDTOMapper;
import io.dav.billpal.model.User;
import io.dav.billpal.repository.UserRepository;
import io.dav.billpal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Dave AKN
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
