package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.CategoryDAO;
import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
@Transactional
    @Override
    public Category save(Category entity) throws ServiceException {
        LOG.info("Try to save category: {}", entity);
        try {
            return categoryDAO.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save category!", e);
        }
    }
    @Transactional
    @Override
    public Category getById(int id) throws ServiceException {
        LOG.info("Try to find category, id: {}", id);
        try {
            return categoryDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
    }
    @Transactional
    @Override
    public List<Category> getAll() throws ServiceException {
        LOG.info("Try to find categories...");
        try {
            return categoryDAO.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any category!", e);
        }
    }
    @Transactional
    @Override
    public Category getCategoryWithLimitedProductList(int categoryId, int currentPage, int productsCount)
            throws ServiceException {
        LOG.info("Try to get limited product list from category, categoryId: {}", categoryId);
        try {
            return categoryDAO.getCategoryWithLimitedProductList(categoryId, currentPage, productsCount);
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
    }
    @Transactional
    @Override
    public void update(Category entity) throws ServiceException {
        LOG.info("Try to update category: {}", entity);
        try {
            categoryDAO.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update category!", e);
        }
    }
    @Transactional
    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete category, id: {}", id);
        try {
            categoryDAO.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete category!", e);
        }
    }
}
