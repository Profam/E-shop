package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    Order saveOrderProductsInfo(Order entity, List<Product> productList) throws ServiceException;
    List<Order> getOrdersWithProductsByUserId(int userId) throws ServiceException;
}
