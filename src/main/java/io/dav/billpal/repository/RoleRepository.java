package io.dav.billpal.repository;

import io.dav.billpal.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Dave AKN
 * @version 1.0
 */
@Repository
public interface RoleRepository<T extends Role> {
    /**
     * basic CRUD operations
     */
    T create(T data);

    Collection<T> list (int page, int pageSize);

    T get(Long id);

    T update(T data);

    Boolean delete(Long id);

    /**
     * more complex operations
     */
    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
}
