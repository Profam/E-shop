package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.exceptions.ServiceException;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    List<Order> getOrdersWithProductsByUserId(String userId) throws ServiceException;

    void createOrder(String userId, String totalPrice) throws ServiceException;

    boolean isNotAuthorized(String userId);
}
