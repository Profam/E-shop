package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;
import java.util.Map;

public interface ProductDAO extends BaseDAO<Product> {
    List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int recordsCount)
            throws ConnectionPoolException, DaoException;

    long getCountOfProductsFromCategory(int categoryId) throws ConnectionPoolException, DaoException;
}
