package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.BaseEntity;
import com.oxagile.eshop.exceptions.ServiceException;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T save(T entity) throws ServiceException;
    T getById(int id) throws ServiceException;
    List<T> getAll() throws  ServiceException;
    void update(T entity) throws ServiceException;
    void deleteById(int id) throws ServiceException;
}
