package io.dav.billpal.repository.impl;

import io.dav.billpal.exception.ApiException;
import io.dav.billpal.model.Role;
import io.dav.billpal.model.User;
import io.dav.billpal.repository.RoleRepository;
import io.dav.billpal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.dav.billpal.enumeration.RoleType.ROLE_USER;
import static io.dav.billpal.query.UserQuery.COUNT_USER_EMAIL_QUERY;
import static io.dav.billpal.query.UserQuery.INSERT_USER_QUERY;
import static java.util.Objects.requireNonNull;

/**
 * @author Dave AKN
 * @version 1.0
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RoleRepository<Role> roleRepository;

    @Override
    public User create(User user) {
        // check the email is unique
        if(this.getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");

        // save new user
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameters = this.getSqlParameterSource(user);
            this.jdbcTemplate.update(INSERT_USER_QUERY, parameters, keyHolder);
            user.setId(requireNonNull(keyHolder.getKey()).longValue());

            // add role to the user
            this.roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            // send verification url
            // save url in verification table
            // send email to user with verification url
            // return newly created user
            // throw exception with appropriate message if error occurs
        } catch (EmptyResultDataAccessException exception) {

        } catch (Exception exception) {

        }
        return null;
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
        return this.jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource();
    }
}
