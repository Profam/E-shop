package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.daoimpl.ProductDAOImpl;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductDAOImpl productDAOImpl;

    public ProductServiceImpl(ProductDAOImpl productDAOImpl) {
        this.productDAOImpl = productDAOImpl;
    }

    @Override
    public Product save(Product entity) throws ServiceException {
        log.info("Try to save product... : " + entity.toString());
        try {
            return productDAOImpl.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save product!", e);
        }
    }

    @Override
    public Product getById(int id) throws ServiceException {
        log.info("Try to find product... : " + id);
        try {
            return productDAOImpl.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find product by id!", e);
        }
    }

    @Override
    public List<Product> getAll() throws ServiceException {
        log.info("Try to find products...");
        try {
            return productDAOImpl.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any product!", e);
        }
    }

    @Override
    public long getCountOfProductsForCategory(int categoryId) throws ServiceException {
        log.info("Try to find count of products from category, categoryId : " + categoryId);
        try {
            return productDAOImpl.getCountOfProductsFromCategory(categoryId);
        } catch (Exception e) {
            throw new ServiceException("Failed to find count of products by category id!", e);
        }
    }

    @Override
    public List<Product> getProductsListByParams(Map<String, String> parameters, int currentPage, int recordsCount) throws ServiceException {
        log.info("Try to find products by parameters... ");
        try {
            return productDAOImpl.getProductsListByParams(parameters, currentPage, recordsCount);
        } catch (Exception e) {
            throw new ServiceException("Failed to find  products by parameters!", e);
        }
    }

    @Override
    public void update(Product entity) throws ServiceException {
        log.info("Try to update product... " + entity.toString());
        try {
            productDAOImpl.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update product!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("Try to delete product... " + id);
        try {
            productDAOImpl.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete product!", e);
        }
    }
}