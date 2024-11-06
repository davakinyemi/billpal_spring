package io.dav.billpal.repository;

import io.dav.billpal.model.Role;

import java.util.Collection;

/**
 * @author Dave AKN
 * @version 1.0
 */
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
}
