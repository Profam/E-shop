package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.OrderDAO;
import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.OrderService;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderDAO orderDAO;
    private final UserService userService;

    public OrderServiceImpl(OrderDAO orderDAO, UserService userService) {
        this.orderDAO = orderDAO;
        this.userService = userService;
    }

    @Override
    public Order save(Order entity) throws ServiceException {
        LOG.info("Try to save order: {}", entity);
        try {
            return orderDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save order!", e);
        }
    }

    @Override
    public Order getById(int id) throws ServiceException {
        LOG.info("Try to find order: {}", id);
        try {
            Optional<Order> orderOptional = orderDAO.findById(id);
            if (orderOptional.isPresent()) {
                return orderOptional.get();
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find order!", e);
        }
        return new Order();
    }

    @Transactional
    @Override
    public List<Order> getOrdersWithProductsByUserId(int userId) throws ServiceException {
        LOG.info("Try to find orders with products by userId: {}", userId);
        try {
            return orderDAO.findByUserIdOrderByIdDesc(userId);
        } catch (Exception e) {
            throw new ServiceException("Failed to find orders with products!", e);
        }
    }

    @Override
    public List<Order> getAll() throws ServiceException {
        LOG.info("Try to find orders...");
        try {
            return (List<Order>) orderDAO.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any order!", e);
        }
    }

    @Override
    public void update(Order entity) throws ServiceException {
        LOG.info("Try to update order: {}", entity);
        try {
            orderDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update order!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete order, id: {}", id);
        try {
            if (orderDAO.existsById(id)) {
                orderDAO.deleteById(id);
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to delete order!", e);
        }
    }

    @Override
    public void createOrder(String email, String totalPrice, List<Product> products) throws ServiceException {
        save(Order.newBuilder()
                .withUserId(userService.findByUserEmail(email).getId())
                .withDate(Date.valueOf(LocalDate.now()))
                .withPrice(Integer.parseInt(totalPrice))
                .withProducts(products)
                .build());
    }
}
