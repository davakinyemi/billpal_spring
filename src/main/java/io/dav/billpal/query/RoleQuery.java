package io.dav.billpal.query;

/**
 * @author Dave AKN
 * @version 1.0
 */
public class RoleQuery {
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)";
    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM roles WHERE name = :name";
}
