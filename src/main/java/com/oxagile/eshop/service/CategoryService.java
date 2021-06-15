package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ServiceException;

public interface CategoryService extends BaseService<Category> {
    Category getCategoryWithLimitedProductList(int categoryId, int currentPage, int productsCount)
            throws ServiceException;
}
