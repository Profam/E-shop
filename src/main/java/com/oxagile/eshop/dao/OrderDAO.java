package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.Order;

import java.util.List;

public interface OrderDAO extends BaseDAO<Order> {
    List<Order> findByUserIdOrderByIdDesc(int userId);
}