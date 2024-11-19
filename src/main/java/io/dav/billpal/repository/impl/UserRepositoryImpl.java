package io.dav.billpal.repository.impl;

import io.dav.billpal.exception.ApiException;
import io.dav.billpal.model.Role;
import io.dav.billpal.model.User;
import io.dav.billpal.model.UserPrincipal;
import io.dav.billpal.repository.RoleRepository;
import io.dav.billpal.repository.UserRepository;
import io.dav.billpal.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static io.dav.billpal.enumeration.RoleType.ROLE_USER;
import static io.dav.billpal.enumeration.VerificationType.ACCOUNT;
import static io.dav.billpal.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of UserRepository for managing User entities
 * @author Dave AKN
 * @version 1.0
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {

        if(this.getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameters = this.getSqlParameterSource(user);

            this.jdbcTemplate.update(INSERT_USER_QUERY, parameters, keyHolder);
            user.setId(requireNonNull(keyHolder.getKey()).longValue());

            this.roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            String verificationUrl = this.getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            this.jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));

            user.setEnabled(false);
            user.setNotLocked(true);

            return user;

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while creating user: " + exception.getMessage() + ". Please try again.");
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return List.of();
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return this.jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", this.passwordEncoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.getUserByEmail(email);
        if (user == null) {
            log.error("User could not be found with email: {}", email);
            throw new UsernameNotFoundException("User could not be found with email: " + email);
        } else {
            log.info("User found with email: {}", email);
            return new UserPrincipal(user, this.roleRepository.getRoleByUserId(user.getId()).getPermission());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return this.jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("User could not be found with email: " + email);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
}
