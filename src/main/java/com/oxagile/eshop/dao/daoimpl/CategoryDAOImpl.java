package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.CategoryDAO;
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
import javax.persistence.criteria.Root;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger LOG = LogManager.getLogger(CategoryDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Category create(Category category) {
        LOG.debug("Creating new category: {}", category);
        entityManager.persist(category);
        return category;
    }
    @Transactional
    @Override
    public Category read(int id) {
        LOG.debug("Read category, id: {}", id);
        return entityManager.find(Category.class, id);
    }
    @Transactional
    @Override
    public List<Category> readAll() {
        LOG.debug("Read all categories");
        return (List<Category>) entityManager.createQuery("select c from Category c").getResultList();
    }
    @Transactional
    @Override
    public void update(Category category) {
        LOG.debug("Update category: {}", category);
        entityManager.merge(category);
    }
    @Transactional
    @Override
    public void delete(int id) {
        LOG.debug("Delete category, categoryId: {}", id);
        entityManager.remove(entityManager.find(Category.class, id));
    }
    @Transactional
    @Override
    public Category getCategoryWithLimitedProductList(int id, int currentPage, int recordsPerPage) {
        LOG.debug("get category with limited product list, categoryId: {}", id);
        Category category = entityManager.find(Category.class, id);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> from = cq.from(Product.class);
        CriteriaQuery<Product> limitProductsByCategoryId =
                cq.select(from).where(cb.equal(from.get(CATEGORY_ID_PARAM), id));
        TypedQuery<Product> allQuery = entityManager.createQuery(limitProductsByCategoryId);
        allQuery.setFirstResult(recordsPerPage * (currentPage - 1));
        allQuery.setMaxResults(recordsPerPage);
        category.setProductList(allQuery.getResultList());
        return category;
    }
}
