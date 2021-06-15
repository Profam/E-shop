package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.ProductDAO;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_FROM_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_TO_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.SEARCH_REQUEST_PARAM;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private static final Logger log = LogManager.getLogger(ProductDAOImpl.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Product create(Product product) {
        log.info("Creating new product: " + product.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return product;
    }

    @Override
    public Product read(int id) {
        log.info("Read product, id: " + id);
        EntityManager em = emf.createEntityManager();
        Product product = null;
        try {
            em.getTransaction().begin();
            product = em.find(Product.class, id);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return product;
    }

    @Override
    public List<Product> readAll() {
        log.info("Read all products");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> rootEntry = cq.from(Product.class);
        CriteriaQuery<Product> all = cq.select(rootEntry);
        TypedQuery<Product> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void update(Product product) {
        log.info("Update product: " + product.toString());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Delete product, productid: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            em.remove(product);
            em.getTransaction().commit();
        } catch (HibernateException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public long getCountOfProductsFromCategory(int categoryId) {
        log.info("Read count of products from categoryId" + categoryId);
        EntityManager em = emf.createEntityManager();
        long numberOfRows = 0;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Product> from = cq.from(Product.class);
            cq.select(cb.count(from));
            cq.where(cb.equal(from.get("categoryId"), categoryId));
            numberOfRows = em.createQuery(cq).getSingleResult();
            return numberOfRows;
        } catch (HibernateException e) {
            log.info("Failed to count of products from database!");
        } finally {
            em.close();
        }
        return numberOfRows;
    }

    @Override
    public List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int recordsCount) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> from = cq.from(Product.class);
            List<Predicate> predicates = new ArrayList<>();
            if (parameters.containsKey(CATEGORY_ID_PARAM)) {
                //" INNER JOIN categories ON products.CATEGORY_ID = categories.ID WHERE categories.name='"
                Join<Product, Category> categoryJoin = from.join("category");
                predicates.add((cb.equal(categoryJoin.get("name"), parameters.get(CATEGORY_ID_PARAM))));
                if (parameters.containsKey(SEARCH_REQUEST_PARAM)) {
                    //" AND (products.name LIKE '%" ?"%' OR products.description LIKE '%"
                    predicates.add(cb.or(
                            cb.and(cb.like(from.get("name"), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")),
                            cb.like(from.get("description"), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")));
                }
            } else {
                if (!parameters.containsKey(SEARCH_REQUEST_PARAM)) {
                    //" WHERE products.id"
                    cq.select(from).where(from.get("id"));
                } else {
                    //WHERE (products.name LIKE '%" OR products.description LIKE '%"
                    predicates.add(cb.or(
                            cb.like(from.get("name"), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%"),
                            cb.like(from.get("description"), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")));
                }
            }
            if (parameters.containsKey(PRODUCT_PRICE_FROM_ATTRIBUTE)) {
                //" AND products.PRICE >= "
                predicates.add(
                        cb.and(cb.ge(from.get("price"), Integer.parseInt(parameters.get(PRODUCT_PRICE_FROM_ATTRIBUTE)))));
            }
            if (parameters.containsKey(PRODUCT_PRICE_TO_ATTRIBUTE)) {
                //" AND products.PRICE <= "
                predicates.add(
                        cb.and(cb.le(from.get("price"), Integer.parseInt(parameters.get(PRODUCT_PRICE_TO_ATTRIBUTE)))));
            }
            cq.select(from)
                    .where(predicates.toArray(new Predicate[]{}));
            //" ORDER BY products.name"
            cq.select(from).orderBy(cb.asc(from.get("name")));
            TypedQuery<Product> allQuery = em.createQuery(cq);
            if (!(currentPage == 1 && recordsCount == 0)) {
                allQuery.setFirstResult(recordsCount * (currentPage - 1));
                allQuery.setMaxResults(recordsCount);
            }
            return allQuery.getResultList();
        } catch (HibernateException e) {
            log.info("Failed to get products by parameters!");
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }
}


