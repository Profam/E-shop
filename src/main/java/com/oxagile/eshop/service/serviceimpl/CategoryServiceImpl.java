package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.daoimpl.CategoryDAOImpl;
import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryDAOImpl categoryDAOImpl;

    public CategoryServiceImpl(CategoryDAOImpl categoryDAOImpl) {
        this.categoryDAOImpl = categoryDAOImpl;
    }

    @Override
    public Category save(Category entity) throws ServiceException {
        log.info("Try to save category... : " + entity.toString());
        try {
            return categoryDAOImpl.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save category!", e);
        }
    }

    @Override
    public Category getById(int id) throws ServiceException {
        log.info("Try to find category... : " + id);
        try {
            return categoryDAOImpl.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
    }

    @Override
    public List<Category> getAll() throws ServiceException {
        log.info("Try to find categories...");
        try {
            return categoryDAOImpl.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any category!", e);
        }
    }

    @Override
    public Category getCategoryWithLimitedProductList(int categoryId, int currentPage, int productsCount)
            throws ServiceException {
        log.info("Try to get limited product list from category... : ");
        try {
            return categoryDAOImpl.getCategoryWithLimitedProductList(categoryId, currentPage, productsCount);
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
    }

    @Override
    public void update(Category entity) throws ServiceException {
        log.info("Try to update category... " + entity.toString());
        try {
            categoryDAOImpl.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update category!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("Try to delete category... " + id);
        try {
            categoryDAOImpl.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete category!", e);
        }
    }
}
