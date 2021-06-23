package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;

public interface OrderDAO extends BaseDAO<Order> {
    List<Order> getOrdersWithProductsByUserId(int userId) throws DaoException;
}