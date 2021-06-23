package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product> {
    List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int productsCount) throws ServiceException;

    long getCountOfProductsForCategory(int categoryId) throws ServiceException;

    List<Product> addProductToBasket(int productId, List<Product> products) throws ServiceException;

    List<Product> removeProductFromBasket(int productId, List<Product> products) throws ServiceException;
}

