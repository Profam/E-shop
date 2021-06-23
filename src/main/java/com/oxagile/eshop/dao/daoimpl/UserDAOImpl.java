package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.UserDAO;
import com.oxagile.eshop.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_EMAIL_PARAM;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public User create(User user) {
        LOG.debug("Creating new user: {}", user);
        entityManager.persist(user);
        return user;
    }
    @Transactional
    @Override
    public User read(int id) {
        LOG.debug("Read user, userId: {}", id);
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        LOG.debug("Read user email, email: {}", email);
        Query query = entityManager.createQuery("select u from User u where u.email = :email");
        query.setParameter(USER_EMAIL_PARAM, email);
        return (User) query.getSingleResult();
    }
    @Transactional
    @Override
    public List<User> readAll() {
        LOG.debug("Read all users");
        return (List<User>) entityManager.createQuery("select u from User u").getResultList();
    }
    @Transactional
    @Override
    public void update(User user) {
        LOG.debug("Update user: {}", user);
        entityManager.merge(user);
    }
    @Transactional
    @Override
    public void delete(int id) {
        LOG.debug("Delete user, userId: {}", id);
        entityManager.remove(entityManager.find(User.class, id));
    }
}
