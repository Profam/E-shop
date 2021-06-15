package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.ProductImageDAO;
import com.oxagile.eshop.domain.ProductImage;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductImageDAOImpl implements ProductImageDAO {
    private static final Logger log = LogManager.getLogger(ProductImageDAOImpl.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public ProductImage create(ProductImage productImage) {
        log.info("Creating new productImage: " + productImage.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(productImage);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return productImage;
    }

    @Override
    public ProductImage read(int id) {
        log.info("Read productImage, id: " + id);
        EntityManager em = emf.createEntityManager();
        ProductImage productImage = null;
        try {
            em.getTransaction().begin();
            productImage = em.find(ProductImage.class, id);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return productImage;
    }

    @Override
    public List<ProductImage> readAll() {
        log.info("Read all product images");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductImage> cq = cb.createQuery(ProductImage.class);
        Root<ProductImage> rootEntry = cq.from(ProductImage.class);
        CriteriaQuery<ProductImage> all = cq.select(rootEntry);
        TypedQuery<ProductImage> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<ProductImage> getImagesByProductId(int productId) {
        log.info("Read images by product id, id " + productId);
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductImage> cq = cb.createQuery(ProductImage.class);
            Root<ProductImage> rootEntry = cq.from(ProductImage.class);
            CriteriaQuery<ProductImage> allByProductId = cq.select(rootEntry).where(cb.equal(rootEntry.get("productId"), productId));
            TypedQuery<ProductImage> allQuery = em.createQuery(allByProductId);
            return allQuery.getResultList();
        } catch (HibernateException e) {
            log.info("Failed to read images by product id!");
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

    @Override
    public void update(ProductImage productImage) {
        log.info("Update productImage: " + productImage.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(productImage);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Delete productImage, id: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ProductImage productImage = em.find(ProductImage.class, id);
            em.remove(productImage);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
