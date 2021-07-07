package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface BaseDAO<T extends BaseEntity> extends CrudRepository<T, Integer> {
}
