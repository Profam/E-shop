package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.daoimpl.ProductImageDAOImpl;
import com.oxagile.eshop.domain.ProductImage;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.ProductImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private static final Logger log = LogManager.getLogger(ProductImageServiceImpl.class);

    private final ProductImageDAOImpl productImageDAOImpl;

    public ProductImageServiceImpl(ProductImageDAOImpl productImageDAOImpl) {
        this.productImageDAOImpl = productImageDAOImpl;
    }

    @Override
    public ProductImage save(ProductImage entity) throws ServiceException {
        log.info("Try to save product image... : " + entity.toString());
        try {
            return productImageDAOImpl.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save product image!", e);
        }
    }

    @Override
    public ProductImage getById(int id) throws ServiceException {
        log.info("Try to find product image... : " + id);
        try {
            return productImageDAOImpl.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find product image!", e);
        }
    }

    @Override
    public List<ProductImage> getAll() throws ServiceException {
        log.info("Try to find product image...");
        try {
            return productImageDAOImpl.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any product image!", e);
        }
    }

    @Override
    public void update(ProductImage entity) throws ServiceException {
        log.info("Try to update product image... " + entity.toString());
        try {
            productImageDAOImpl.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update product image!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("Try to delete product image... " + id);
        try {
            productImageDAOImpl.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete product image!", e);
        }
    }
}
