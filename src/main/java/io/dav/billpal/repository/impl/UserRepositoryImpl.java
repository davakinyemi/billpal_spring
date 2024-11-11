package io.dav.billpal.repository.impl;

import io.dav.billpal.exception.ApiException;
import io.dav.billpal.model.Role;
import io.dav.billpal.model.User;
import io.dav.billpal.repository.RoleRepository;
import io.dav.billpal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        // check the email is unique
        if(this.getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");

        // save new user
        try {
            // Prepare for inserting new user
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameters = this.getSqlParameterSource(user);

            // Insert the new user and get the generated ID
            this.jdbcTemplate.update(INSERT_USER_QUERY, parameters, keyHolder);
            user.setId(requireNonNull(keyHolder.getKey()).longValue());

            // Add default role (ROLE_USER) to the new user
            this.roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            // Generate verification URL
            String verificationUrl = this.getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            // Save verification URL in the database
            this.jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));

            // send email to user with verification url
            // this.emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);

            // Set initial user status
            user.setEnabled(false);
            user.setNotLocked(true);

            // return newly created user
            return user;

            // throw exception with appropriate message if error occurs
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while creating user: " + exception.getMessage() + ". Please try again.");
        }
    }

    // The following methods are not implemented in this class
    // They are likely placeholder methods to be implemented later
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

    /**
     * Counts the number of users with the given email
     * @param email The email to check
     * @return The count of users with the given email
     */
    private Integer getEmailCount(String email) {
        return this.jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    /**
     * Creates SqlParameterSource for user insertion
     * @param user The user to create parameters for
     * @return SqlParameterSource with user details
     */
    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", this.passwordEncoder.encode(user.getPassword()));
    }

    /**
     * Generates a verification URL
     * @param key Unique key for verification
     * @param type Type of verification
     * @return The generated verification URL
     */
    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }
}

/**
 * Additional notes for overall understanding:
 * 1. This UserRepositoryImpl class implements the UserRepository interface for managing User entities.
 * 2. It's annotated with @Repository, indicating that it's a Spring Data Access Object.
 * 3. @RequiredArgsConstructor generates a constructor for all final fields, enabling constructor-based dependency injection.
 * 4. @Slf4j adds a logger field to the class for logging.
 * 5. The class uses NamedParameterJdbcTemplate for database operations, which allows for more readable SQL queries with named parameters.
 * 6. The create method is the main implemented functionality:
 *      - It checks for email uniqueness before creating a user.
 *      - It inserts the new user into the database and retrieves the generated ID.
 *      - It adds a default role (ROLE_USER) to the new user.
 *      - It generates and saves a verification URL for account activation.
 *      - It sets initial user status (not enabled, not locked).
 * 7. The class uses static imports for SQL queries and enums, suggesting these are defined in separate utility classes.
 * 8. Password encoding is handled using BCryptPasswordEncoder.
 * 9. The getEmailCount, getSqlParameterSource, and getVerificationUrl are private helper methods that support the main functionality.
 * 10. Most of the other methods from the UserRepository interface are not implemented in this class (returning null or empty collections).
 *     This might indicate that the class is still under development or that these operations are handled elsewhere.
 * 11. Error handling is implemented, with custom ApiExceptions thrown for specific scenarios.
 * 12. There's a commented-out section for sending verification emails, which would need to be implemented and uncommented for full functionality.
 * This implementation focuses on user creation with associated processes like role assignment and account verification. As the project develops,
 * you might want to implement the other methods and consider adding more robust error handling and logging.
 */
