package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.ProductDAO;
import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_NAME_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ENTITY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_DESCRIPTION_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_NAME_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_FROM_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_TO_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.SEARCH_REQUEST_PARAM;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private static final Logger LOG = LogManager.getLogger(ProductDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Product create(Product product) {
        LOG.debug("Creating new product: {}", product);
        entityManager.persist(product);
        return product;
    }
    @Transactional
    @Override
    public Product read(int id) {
        LOG.debug("Read product, id: {}", id);
        return entityManager.find(Product.class, id);
    }
    @Transactional
    @Override
    public List<Product> readAll() {
        LOG.debug("Read all products");
        return (List<Product>) entityManager.createQuery("select p from Product p").getResultList();
    }
    @Transactional
    @Override
    public void update(Product product) {
        LOG.debug("Update product: {}", product);
        entityManager.merge(product);
    }
    @Transactional
    @Override
    public void delete(int id) {
        LOG.debug("Delete product, productid: {}", id);
        entityManager.remove(entityManager.find(Product.class, id));
    }
    @Transactional
    @Override
    public long getCountOfProductsFromCategory(int categoryId) {
        LOG.debug("Read count of products from categoryId: {}", categoryId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> from = cq.from(Product.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get(CATEGORY_ID_PARAM), categoryId));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int recordsCount) {
        LOG.debug("Read products by parameters: {}", parameters);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> from = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (parameters.containsKey(CATEGORY_ID_PARAM)) {
            //" INNER JOIN categories ON products.CATEGORY_ID = categories.ID WHERE categories.name='"
            Join<Product, Category> categoryJoin = from.join(CATEGORY_PARAM);
            predicates.add((cb.equal(categoryJoin.get(CATEGORY_NAME_PARAM), parameters.get(CATEGORY_ID_PARAM))));
            if (parameters.containsKey(SEARCH_REQUEST_PARAM)) {
                //" AND (products.name LIKE '%" ?"%' OR products.description LIKE '%"
                predicates.add(cb.or(
                        cb.and(cb.like(from.get(PRODUCT_NAME_PARAM), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")),
                        cb.like(from.get(PRODUCT_DESCRIPTION_PARAM), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")));
            }
        } else {
            if (!parameters.containsKey(SEARCH_REQUEST_PARAM)) {
                //" WHERE products.id"
                cq.select(from).where(from.get(ENTITY_ID_PARAM));
            } else {
                //WHERE (products.name LIKE '%" OR products.description LIKE '%"
                predicates.add(cb.or(
                        cb.like(from.get(PRODUCT_NAME_PARAM), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%"),
                        cb.like(from.get(PRODUCT_DESCRIPTION_PARAM), "%" + parameters.get(SEARCH_REQUEST_PARAM) + "%")));
            }
        }
        if (parameters.containsKey(PRODUCT_PRICE_FROM_ATTRIBUTE)) {
            //" AND products.PRICE >= "
            predicates.add(
                    cb.and(cb.ge(from.get(PRODUCT_PRICE_PARAM), Integer.parseInt(parameters.get(PRODUCT_PRICE_FROM_ATTRIBUTE)))));
        }
        if (parameters.containsKey(PRODUCT_PRICE_TO_ATTRIBUTE)) {
            //" AND products.PRICE <= "
            predicates.add(
                    cb.and(cb.le(from.get(PRODUCT_PRICE_PARAM), Integer.parseInt(parameters.get(PRODUCT_PRICE_TO_ATTRIBUTE)))));
        }
        cq.select(from)
                .where(predicates.toArray(new Predicate[]{}));
        //" ORDER BY products.name"
        cq.select(from).orderBy(cb.asc(from.get(PRODUCT_NAME_PARAM)));
        TypedQuery<Product> allQuery = entityManager.createQuery(cq);
        if (!(currentPage == 1 && recordsCount == 0)) {
            allQuery.setFirstResult(recordsCount * (currentPage - 1));
            allQuery.setMaxResults(recordsCount);
        }
        return allQuery.getResultList();
    }
}




