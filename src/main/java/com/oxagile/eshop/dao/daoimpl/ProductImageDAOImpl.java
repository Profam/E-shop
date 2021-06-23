package com.oxagile.eshop.dao.daoimpl;

import com.oxagile.eshop.dao.ProductImageDAO;
import com.oxagile.eshop.domain.ProductImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductImageDAOImpl implements ProductImageDAO {
    private static final Logger LOG = LogManager.getLogger(ProductImageDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ProductImage create(ProductImage productImage) {
        LOG.debug("Creating new productImage: {}", productImage);
        entityManager.persist(productImage);
        return productImage;
    }
    @Transactional
    @Override
    public ProductImage read(int id) {
        LOG.debug("Read productImage, id: {}", id);
        return entityManager.find(ProductImage.class, id);
    }
    @Transactional
    @Override
    public List<ProductImage> readAll() {
        LOG.debug("Read all product images");
        return (List<ProductImage>) entityManager.createQuery("select pi from ProductImage pi").getResultList();
    }
    @Transactional
    @Override
    public void update(ProductImage productImage) {
        LOG.debug("Update productImage: {}", productImage);
        entityManager.merge(productImage);
    }
    @Transactional
    @Override
    public void delete(int id) {
        LOG.debug("Delete productImage, id: {}", id);
        entityManager.remove(entityManager.find(ProductImage.class, id));
    }
}

