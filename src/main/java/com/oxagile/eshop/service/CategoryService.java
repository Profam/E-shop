package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ServiceException;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category> {
    Category getCategoryWithLimitedProductList(int categoryId, int currentPage, int productsCount)
            throws ServiceException;

    ModelAndView getLimitedProductListFromCategory(ModelAndView modelAndView, int categoryId,
                                                   int currentPage, String recordsPerPage) throws ServiceException;

    int getCountOfProductsForCategory(int categoryId) throws ServiceException;
}
