package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDAO extends BaseDAO<Product>, JpaSpecificationExecutor<Product> {
    int countProductsByCategoryId(int categoryId);

    Page<Product> findProductByCategoryId(int categoryId, Pageable pageable);
}