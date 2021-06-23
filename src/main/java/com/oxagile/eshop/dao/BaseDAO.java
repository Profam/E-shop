package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.BaseEntity;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;

public interface BaseDAO<T extends BaseEntity> {
    T create(T entity) throws DaoException;

    T read(int id) throws DaoException;

    List<T> readAll() throws DaoException;

    void update(T entity) throws DaoException;

    void delete(int id) throws DaoException;
}
