package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.ProductDAO;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    @Transactional
    @Override
    public Product save(Product entity) throws ServiceException {
        LOG.info("Try to save product: {}", entity);
        try {
            return productDAO.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save product!", e);
        }
    }
    @Transactional
    @Override
    public Product getById(int id) throws ServiceException {
        LOG.info("Try to find product, id: {}", id);
        try {
            return productDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find product by id!", e);
        }
    }
    @Transactional
    @Override
    public List<Product> getAll() throws ServiceException {
        LOG.info("Try to find products...");
        try {
            return productDAO.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any product!", e);
        }
    }
    @Transactional
    @Override
    public long getCountOfProductsForCategory(int categoryId) throws ServiceException {
        LOG.info("Try to find count of products from category, categoryId: {}", categoryId);
        try {
            return productDAO.getCountOfProductsFromCategory(categoryId);
        } catch (Exception e) {
            throw new ServiceException("Failed to find count of products by category id!", e);
        }
    }
    @Transactional
    @Override
    public List<Product> getProductsListByParams
            (Map<String, String> parameters, int currentPage, int recordsCount) throws ServiceException {
        LOG.info("Try to find products by parameters: {}", parameters);
        try {
            return productDAO.getProductsListByParams(parameters, currentPage, recordsCount);
        } catch (Exception e) {
            throw new ServiceException("Failed to find  products by parameters!", e);
        }
    }
    @Transactional
    @Override
    public void update(Product entity) throws ServiceException {
        LOG.info("Try to update product: {}", entity);
        try {
            productDAO.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update product!", e);
        }
    }
    @Transactional
    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete product, id: {}", id);
        try {
            productDAO.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete product!", e);
        }
    }
    @Transactional
    @Override
    public List<Product> addProductToBasket(int productId, List<Product> products) throws ServiceException {
        Product product = getById((productId));
        if (!products.contains(product)) {
            products.add(product);
        }
        return products;
    }
    @Transactional
    @Override
    public List<Product> removeProductFromBasket(int productId, List<Product> products) throws ServiceException {
        Product product = getById(productId);
        products.removeIf(productInList -> productInList.getId() == product.getId());
        return products;
    }
}