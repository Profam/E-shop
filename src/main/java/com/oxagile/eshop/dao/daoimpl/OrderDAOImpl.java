package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.OrderDAO;
import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.domain.ProductImage;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private static final Logger log = LogManager.getLogger(OrderDAOImpl.class);
    ProductImageDAOImpl productImageDAOImpl = new ProductImageDAOImpl();

    @PersistenceUnit
    private EntityManagerFactory emf;
    private Connection connection;


    @Override
    public Order create(Order order) {
        log.info("Creating new order: " + order.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return order;
    }

    private static final String ADD_ORDER_PRODUCTS_INFO = "INSERT INTO orders_products VALUES (?,?)";

    @Override
    public Order createOrderProductsInfo(Order order, List<Product> productList) {
        log.info("Creating new order: " + order.toString());
        EntityManager em = emf.createEntityManager();
        try {
            for (Product product : productList) {
                em.createNativeQuery(ADD_ORDER_PRODUCTS_INFO)
                        .setParameter(1, order.getId())
                        .setParameter(2, product.getId())
                        .executeUpdate();
                em.joinTransaction();
            }
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return order;
    }

    @Override
    public Order read(int id) {
        log.info("Read order, id: " + id);
        EntityManager em = emf.createEntityManager();
        Order order = null;
        try {
            em.getTransaction().begin();
            order = em.find(Order.class, id);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return order;
    }

    private static final String GET_ALL_PRODUCTS_FOR_ORDERS_BY_USER_ID = "SELECT orders.id, orders.USER_ID," +
            " orders.DATE, orders_products.PRODUCT_ID, t.NAME, t.DESCRIPTION, t.PRICE FROM orders " +
            "join orders_products on orders.ID=orders_products.ORDER_ID " +
            "join products t on orders_products.PRODUCT_ID=t.id  " +
            "where orders.USER_ID=? order by id desc";


    @Override
    public List<Order> getOrdersWithProductsByUserId(int userId) throws ConnectionPoolException, DaoException {
        log.info("Read all orders by userId, userId: " + userId);
        EntityManager em = emf.createEntityManager();
        try {
            /*CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> from = cq.from(Order.class);
            Join<Order, Product> productJoin = from.join("products");
            cq.where(cb.equal(productJoin.get("user_id"),userId ));
            cq.select(from).where(from.get("id"));
            //" ORDER by id desc"
            cq.select(from).orderBy(cb.desc(from.get("id")));*/
            TypedQuery<Order> allQuery = em.createQuery(GET_ALL_PRODUCTS_FOR_ORDERS_BY_USER_ID, Order.class);
            return allQuery.getResultList();
        } catch (HibernateException e) {
            log.info("Failed to get products by parameters!");
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

        /*List<Order> orders = new ArrayList<>();
        connection = databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_PRODUCTS_FOR_ORDERS_BY_USER_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            int checkId = 0;
            int currentId;
            List<Product> products;
            while (resultSet.next()) {
                if (resultSet.isFirst()) {
                    checkId = resultSet.getInt("id");
                }
                currentId = checkId;
                products = new ArrayList<>();
                int productId;
                while (currentId == checkId && !resultSet.isAfterLast()) {
                    productId = resultSet.getInt("product_id");
                    List<ProductImage> images = productImageDAOImpl.getImagesByProductId(productId);
                    products.add(Product.newBuilder()
                            .withName(resultSet.getString("name"))
                            .withDescription(resultSet.getString("description"))
                            .withPrice(resultSet.getInt("price"))
                            .withImages(images)
                            .build());
                    if (resultSet.next()) {
                        checkId = resultSet.getInt("id");
                    }
                }
                if (!resultSet.isFirst() || resultSet.isAfterLast()) {
                    resultSet.previous();
                }
                orders.add(Order.newBuilder()
                        .withId(resultSet.getInt("id"))
                        .withDate(resultSet.getDate("date"))
                        .withProducts(products)
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to read orders and products!", e);
        } finally {
            databaseConnection.closeConnection(connection);
        }
        return orders;
    }*/

    @Override
    public List<Order> readAll() {
        log.info("Read all orders");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> rootEntry = cq.from(Order.class);
        CriteriaQuery<Order> all = cq.select(rootEntry);
        TypedQuery<Order> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void update(Order order) {
        log.info("Update order: " + order.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Delete order, orderId: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Order order = em.find(Order.class, id);
            em.remove(order);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
