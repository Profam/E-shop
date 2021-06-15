package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;

public interface OrderDAO extends BaseDAO<Order>{
    Order createOrderProductsInfo(Order order, List<Product> productList) throws ConnectionPoolException, DaoException;
    List<Order> getOrdersWithProductsByUserId(int userId) throws ConnectionPoolException, DaoException;
}
