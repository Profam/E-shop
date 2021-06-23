package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.OrderDAO;
import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    @Transactional
    @Override
    public Order save(Order entity) throws ServiceException {
        LOG.info("Try to save order: {}", entity);
        try {
            return orderDAO.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save order!", e);
        }
    }
    @Transactional
    @Override
    public Order getById(int id) throws ServiceException {
        LOG.info("Try to find order: {}", id);
        try {
            return orderDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find order!", e);
        }
    }
    @Transactional
    @Override
    public List<Order> getOrdersWithProductsByUserId(String userId) throws ServiceException {
        LOG.info("Try to find orders with products by userId: {}", userId);
        try {
            return orderDAO.getOrdersWithProductsByUserId(Integer.parseInt(userId));
        } catch (Exception e) {
            throw new ServiceException("Failed to find orders with products!", e);
        }
    }
    @Transactional
    @Override
    public List<Order> getAll() throws ServiceException {
        LOG.info("Try to find orders...");
        try {
            return orderDAO.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any order!", e);
        }
    }
    @Transactional
    @Override
    public void update(Order entity) throws ServiceException {
        LOG.info("Try to update order: {}", entity);
        try {
            orderDAO.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update order!", e);
        }
    }
    @Transactional
    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete order, id: {}", id);
        try {
            orderDAO.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete order!", e);
        }
    }
    @Transactional
    @Override
    public void createOrder(String userId, String totalPrice) throws ServiceException {
        save(Order.newBuilder()
                .withUserId(Integer.parseInt(userId))
                .withDate(Date.valueOf(LocalDate.now()))
                .withPrice(Integer.parseInt(totalPrice))
                .build());
    }
    @Transactional
    @Override
    public boolean isNotAuthorized(String userId) {
        return userId.isEmpty();
    }
}
