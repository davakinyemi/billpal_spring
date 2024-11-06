package io.dav.billpal.repository.impl;

import io.dav.billpal.exception.ApiException;
import io.dav.billpal.model.Role;
import io.dav.billpal.repository.RoleRepository;
import io.dav.billpal.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static io.dav.billpal.enumeration.RoleType.ROLE_USER;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {

    private static final String SELECT_ROLE_BY_NAME_QUERY = "";
    private static final String INSERT_ROLE_TO_USER_QUERY = "";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return List.of();
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);

        try {
            Role role = this.jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("roleName",roleName), new RoleRowMapper());
            this.jdbcTemplate.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            throw new ApiException("An error occurred while creating user: " + exception.getMessage() + ". Please try again.");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
