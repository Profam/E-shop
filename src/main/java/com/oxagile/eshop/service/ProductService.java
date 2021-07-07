package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product> {
    List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int productsCount)
            throws ServiceException;

    List<Product> addProductToBasket(int productId, List<Product> products) throws ServiceException;

    List<Product> removeProductFromBasket(int productId, List<Product> products) throws ServiceException;

    ModelAndView getSearchResult(ModelAndView modelAndView, String searchParam,
                                 String categoryIdParam, String priceFromParam,
                                 String priceToParam, String recordsPerPageParam,
                                 String currentPageParam) throws ServiceException;
}
