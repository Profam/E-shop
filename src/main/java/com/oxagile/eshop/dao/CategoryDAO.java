package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

public interface CategoryDAO extends BaseDAO<Category>{
    Category getCategoryWithLimitedProductList(int id, int currentPage, int recordsPerPage) throws DaoException, ConnectionPoolException;
}
