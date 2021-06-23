package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.ProductImageDAO;
import com.oxagile.eshop.domain.ProductImage;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.ProductImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private static final Logger LOG = LogManager.getLogger(ProductImageServiceImpl.class);

    private final ProductImageDAO productImageDAO;

    public ProductImageServiceImpl(ProductImageDAO productImageDAO) {
        this.productImageDAO = productImageDAO;
    }
    @Transactional
    @Override
    public ProductImage save(ProductImage entity) throws ServiceException {
        LOG.info("Try to save product image: {}", entity);
        try {
            return productImageDAO.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save product image!", e);
        }
    }
    @Transactional
    @Override
    public ProductImage getById(int id) throws ServiceException {
        LOG.info("Try to find product image, id: {}", id);
        try {
            return productImageDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find product image!", e);
        }
    }
    @Transactional
    @Override
    public List<ProductImage> getAll() throws ServiceException {
        LOG.info("Try to find product images...");
        try {
            return productImageDAO.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any product image!", e);
        }
    }
    @Transactional
    @Override
    public void update(ProductImage entity) throws ServiceException {
        LOG.info("Try to update product image: {}", entity);
        try {
            productImageDAO.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update product image!", e);
        }
    }
    @Transactional
    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete product image, id: {}", id);
        try {
            productImageDAO.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete product image!", e);
        }
    }
}
