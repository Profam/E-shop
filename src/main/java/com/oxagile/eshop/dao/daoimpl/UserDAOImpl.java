package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.UserDAO;
import com.oxagile.eshop.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_EMAIL_PARAM;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public User create(User user) {
        log.info("Creating new user: " + user.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User read(int id) {
        log.info("Read user, userId: " + id);
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Read user email, email: " + email);
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(User.class).using(USER_EMAIL_PARAM, email).load();
    }

    @Override
    public List<User> readAll() {
        log.info("Read all users");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void update(User user) {
        log.info("Update user: " + user.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Delete user, userId: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            em.remove(user);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}