package io.dav.billpal.repository;

import io.dav.billpal.model.User;

import java.util.Collection;

/**
 * @author Dave AKN
 * @version 1.0
 * @param <T>
 */
public interface UserRepository<T extends User> {
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
}
