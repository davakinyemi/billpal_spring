package io.dav.billpal.rowmapper;

import io.dav.billpal.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation for mapping database rows to Role objects
 * @author Dave AKN
 * @version 1.0
 */
public class RoleRowMapper implements RowMapper<Role> {

    /**
     * Maps a row of the result set to a Role object
     *
     * @param resultSet The ResultSet to map (never null)
     * @param rowNum The number of the current row
     * @return The result object for the current row (could be null)
     * @throws SQLException if a SQLException is encountered getting column values
     */
    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        // Use the Role builder to construct a Role object from the ResultSet
        return Role.builder()
                .id(resultSet.getLong("id")) // Map the 'id' column to the Role's id
                .name(resultSet.getString("name")) // Map the 'name' column to the Role's name
                .permission(resultSet.getString("permission")) // Map the 'permission' column to the Role's permission
                .build(); // Build and return the Role object
    }
}


/**
 * Additional notes for overall understanding:
 * 1. This RoleRowMapper class implements Spring's RowMapper interface, specifically for mapping database rows to Role objects.
 * 2. The RowMapper interface is a key part of Spring's JDBC abstraction layer, used to map rows of a ResultSet on a per-row basis.
 * 3. The mapRow method is called for each row in the ResultSet:
 *      - It takes a ResultSet and the current row number as parameters.
 *      - It's responsible for creating and populating a Role object from the current row of the ResultSet.
 * 4. The method uses the builder pattern (as indicated by Role.builder()) to construct the Role object. This suggests that the Role class is using Lombok's @Builder annotation or a similar builder implementation.
 * 5. Each call to resultSet.getXXX("columnName") retrieves a value from the current row:
 *      - getLong("id") retrieves the role's ID as a long.
 *      - getString("name") retrieves the role's name as a String.
 *      - getString("permission") retrieves the role's permission as a String.
 * 6. The method assumes that the ResultSet contains columns named "id", "name", and "permission". If the column names in your database differ, you'll need to adjust these accordingly.
 * 7. Any SQLException that occurs during the mapping process will be thrown. It's typically handled by the calling code (usually Spring's JdbcTemplate).
 * 8. This mapper doesn't do any null checking or type conversion beyond what's provided by the ResultSet methods. If you need more robust error handling or type conversion, you might want to add that logic here.
 * 9. The rowNum parameter isn't used in this implementation, but it's available if you need to do any row-number-based logic.
 * This RoleRowMapper is typically used in conjunction with Spring's JdbcTemplate or NamedParameterJdbcTemplate when executing SQL queries that return Role objects. It provides a clean separation between database
 * access code and the Role domain object.
 */