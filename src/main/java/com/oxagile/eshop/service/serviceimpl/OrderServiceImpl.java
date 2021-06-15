package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.dao.daoimpl.OrderDAOImpl;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAOImpl orderDAOImpl;

    public OrderServiceImpl(OrderDAOImpl orderDAOImpl) {
        this.orderDAOImpl = orderDAOImpl;
    }

    @Override
    public Order save(Order entity) throws ServiceException {
        log.info("Try to save order... : " + entity.toString());
        try {
            return orderDAOImpl.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save order!", e);
        }
    }

    @Override
    public Order saveOrderProductsInfo(Order entity, List<Product> productList) throws ServiceException {
        log.info("Try to save order info... : " + entity.toString());
        try {
            return orderDAOImpl.createOrderProductsInfo(entity, productList);
        } catch (Exception e) {
            throw new ServiceException("Failed to save order info!", e);
        }
    }

    @Override
    public Order getById(int id) throws ServiceException {
        log.info("Try to find order... : " + id);
        try {
            return orderDAOImpl.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find order!", e);
        }
    }

    @Override
    public List<Order> getOrdersWithProductsByUserId(int userId) throws ServiceException {
        log.info("Try to find orders with products by userId... : " + userId);
        try {
            return orderDAOImpl.getOrdersWithProductsByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Failed to find orders with products!", e);
        }
    }

    @Override
    public List<Order> getAll() throws ServiceException {
        log.info("Try to find orders...");
        try {
            return orderDAOImpl.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any order!", e);
        }
    }

    @Override
    public void update(Order entity) throws ServiceException {
        log.info("Try to update order... " + entity.toString());
        try {
            orderDAOImpl.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update order!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("Try to delete order... " + id);
        try {
            orderDAOImpl.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete order!", e);
        }
    }
}
