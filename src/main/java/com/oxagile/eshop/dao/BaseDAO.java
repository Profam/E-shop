package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.BaseEntity;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;

public interface BaseDAO<T extends BaseEntity> {

    ConnectionPool databaseConnection = ConnectionPool.getInstance();

    T create(T entity) throws ConnectionPoolException, DaoException;

    T read(int id) throws ConnectionPoolException, DaoException;

    List<T> readAll() throws ConnectionPoolException, DaoException;

    void update(T entity) throws ConnectionPoolException, DaoException;

    void delete(int id) throws ConnectionPoolException, DaoException;
}
