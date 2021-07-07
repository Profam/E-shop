package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    List<Order> getOrdersWithProductsByUserId(int userId) throws ServiceException;

    void createOrder(String email, String totalPrice, List<Product> products) throws ServiceException;
}
