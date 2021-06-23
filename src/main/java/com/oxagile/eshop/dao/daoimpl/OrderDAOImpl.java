package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.OrderDAO;
import com.oxagile.eshop.domain.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

import static com.oxagile.eshop.controllers.pools.ParamsPool.ENTITY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_ID_PARAM;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Order create(Order order) {
        LOG.debug("Creating new order: {}", order);
        entityManager.persist(order);
        return order;
    }
    @Transactional
    @Override
    public Order read(int id) {
        LOG.debug("Read order, id: {}", id);
        return entityManager.find(Order.class, id);
    }
    @Transactional
    @Override
    public List<Order> getOrdersWithProductsByUserId(int userId) {
        LOG.debug("Read all orders by userId, userId: {}", userId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> from = cq.from(Order.class);
        CriteriaQuery<Order> ordersByUserId =
                cq.select(from.join("products")).where(cb.equal(from.get(USER_ID_PARAM), userId));
        cq.select(from).orderBy(cb.desc(from.get(ENTITY_ID_PARAM)));
        List<Order> orderList = entityManager.createQuery(ordersByUserId).getResultList();
        return orderList.stream().distinct().collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<Order> readAll() {
        LOG.debug("Read all orders");
        return (List<Order>) entityManager.createQuery("select o from Order o").getResultList();
    }
    @Transactional
    @Override
    public void update(Order order) {
        LOG.debug("Update order: {}", order);
        entityManager.merge(order);
    }
    @Transactional
    @Override
    public void delete(int id) {
        LOG.debug("Delete order, orderId: {}", id);
        entityManager.remove(entityManager.find(Order.class, id));
    }
}
