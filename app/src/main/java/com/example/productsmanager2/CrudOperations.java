package com.example.productsmanager2;

import java.util.List;

/**
 * @author Andrew Korzhov
 * @project ProductsManager2
 * @createdAt 05-Nov-19
 */
public interface CrudOperations<T> {
    List<T> listAll();
    T getOne(final Long id);
    long insert(T obj);
    long update(T obj);
    long delete(final Long objId);

}
