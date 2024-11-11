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
import static io.dav.billpal.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static io.dav.billpal.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of RoleRepository interface for managing Role entities
 * @author Dave AKN
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {

    // Dependency injection of NamedParameterJdbcTemplate for database operations
    private final NamedParameterJdbcTemplate jdbcTemplate;

    // The following methods are not implemented in this class
    // They are likely placeholder methods to be implemented later or in a different class
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

    /**
     * Adds a role to a user
     * @param userId The ID of the user
     * @param roleName The name of the role to be added
     * @throws ApiException if the role is not found or if there's an error during the process
     */
    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);

        try {
            // Query the database to get the Role object by its name
            Role role = this.jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());

            // Insert the user-role relationship into the database
            this.jdbcTemplate.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            // If the role is not found in the database
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            // For any other exception
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while creating user: " + exception.getMessage() + ". Please try again.");
        }
    }

    // The following methods are not implemented in this class
    // They are likely placeholder methods to be implemented later or in a different class
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

/**
 * Additional notes for overall understanding:
 * 1. This class, RoleRepositoryImpl, implements the RoleRepository interface for managing Role entities.
 * 2. It's annotated with @Repository, indicating that it's a Spring Data Access Object.
 * 3. @RequiredArgsConstructor is a Lombok annotation that generates a constructor for all final fields, enabling constructor-based dependency injection.
 * 4. @Slf4j is a Lombok annotation that adds a logger field to the class.
 * 5. The class uses NamedParameterJdbcTemplate for database operations, which allows for more readable SQL queries with named parameters.
 * 6. Most of the methods from the RoleRepository interface are not implemented in this class (returning null or empty collections). This might indicate that the class is still under development or that these operations are handled elsewhere.
 * 7. The addRoleToUser method is the main implemented functionality:
 *      - It first logs the operation.
 *      - It then queries the database to find the Role by its name.
 *      - If found, it inserts a new user-role relationship into the database.
 *      - It handles potential exceptions, throwing custom ApiExceptions with informative messages.
 * 8. The class uses static imports for SQL queries (like INSERT_ROLE_TO_USER_QUERY), suggesting these are defined in a separate RoleQuery class.
 * 9. The requireNonNull check ensures that the role object is not null before accessing its ID.
 * 10.The error handling distinguishes between a role not being found (EmptyResultDataAccessException) and other types of exceptions.
 * This implementation focuses on the addRoleToUser operation, with placeholder methods for other operations. As the project develops, you might want to implement the other methods or consider using Spring Data JPA for simpler database operations
 * if appropriate for your project.
 */
