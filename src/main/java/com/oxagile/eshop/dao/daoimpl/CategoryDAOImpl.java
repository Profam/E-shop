package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.CategoryDAO;
import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger log = LogManager.getLogger(CategoryDAOImpl.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Category create(Category category) {
        log.info("Creating new category: " + category.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return category;
    }

    @Override
    public Category read(int id) {
        log.info("Read category, id: " + id);
        EntityManager em = emf.createEntityManager();
        Category category = null;
        try {
            em.getTransaction().begin();
            category = em.find(Category.class, id);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return category;
    }

    @Override
    public List<Category> readAll() {
        log.info("Read all categories");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> rootEntry = cq.from(Category.class);
        CriteriaQuery<Category> all = cq.select(rootEntry);
        TypedQuery<Category> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void update(Category category) {
        log.info("Update category: " + category.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Delete category, categoryId: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Category category = em.find(Category.class, id);
            em.remove(category);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Category getCategoryWithLimitedProductList(int id, int currentPage, int recordsPerPage) {
        log.info("get category with limited product list, categoryId: " + id);
        EntityManager em = emf.createEntityManager();
        Category category = null;
        try {
            em.getTransaction().begin();
            category = em.find(Category.class, id);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> from = cq.from(Product.class);
            CriteriaQuery<Product> limitProductsByCategoryId = cq.select(from).where(cb.equal(from.get("categoryId"), id));
            TypedQuery<Product> allQuery = em.createQuery(limitProductsByCategoryId);
            allQuery.setFirstResult(recordsPerPage*(currentPage-1));
            allQuery.setMaxResults(recordsPerPage);
            category.setProductList(allQuery.getResultList());
            return category;
        } catch (HibernateException e) {
            log.info("Failed to read products by category id!");
        } finally {
            em.close();
        }
        return category;
    }
}