package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.CategoryDAO;
import com.oxagile.eshop.dao.ProductDAO;
import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.CATEGORY_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.NUMBER_OF_PRODUCT_PAGES;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_ALL;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_PER_PAGE;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO, ProductDAO productDAO) {
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }

    @Transactional
    @Override
    public Category save(Category entity) throws ServiceException {
        LOG.info("Try to save category: {}", entity);
        try {
            return categoryDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save category!", e);
        }
    }

    @Override
    public Category getById(int id) throws ServiceException {
        LOG.info("Try to find category, id: {}", id);
        try {
            Optional<Category> categoryOptional = categoryDAO.findById(id);
            if (categoryOptional.isPresent()) {
                return categoryOptional.get();
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
        return new Category();
    }

    @Override
    public List<Category> getAll() throws ServiceException {
        LOG.info("Try to find categories...");
        try {
            return (List<Category>) categoryDAO.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any category!", e);
        }
    }

    @Override
    public void update(Category entity) throws ServiceException {
        LOG.info("Try to update category: {}", entity);
        try {
            categoryDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update category!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete category, id: {}", id);
        try {
            if (categoryDAO.existsById(id)) {
                categoryDAO.deleteById(id);
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to delete category!", e);
        }
    }

    @Transactional
    @Override
    public Category getCategoryWithLimitedProductList(int categoryId, int currentPage, int productsCount)
            throws ServiceException {
        LOG.info("Try to get limited product list from category, categoryId: {}", categoryId);
        try {
            Category category = categoryDAO.findById(categoryId).orElse(null);
            Pageable paging = PageRequest.of(currentPage - 1, productsCount);
            Page<Product> limitedProductList = productDAO.findProductByCategoryId(categoryId, paging);
            if (category != null) {
                category.setProductList(limitedProductList.getContent());
            }
            return category;
        } catch (Exception e) {
            throw new ServiceException("Failed to find category!", e);
        }
    }

    @Override
    public int getCountOfProductsForCategory(int categoryId) throws ServiceException {
        LOG.info("Try to find count of products from category, categoryId: {}", categoryId);
        try {
            return productDAO.countProductsByCategoryId(categoryId);
        } catch (Exception e) {
            throw new ServiceException("Failed to find count of products by category id!", e);
        }
    }

    @Transactional
    @Override
    public ModelAndView getLimitedProductListFromCategory
            (ModelAndView modelAndView, int categoryId, int currentPage, String recordsPerPage) throws ServiceException {
        Category category;
        int numberOfPages = 0;
        int productsCount;
        productsCount = getCountOfProductsForCategory(categoryId);
        if (recordsPerPage.equals("all")) {
            category = getCategoryWithLimitedProductList(categoryId, currentPage, productsCount);
        } else {
            int recordsCount = Integer.parseInt(recordsPerPage);
            if (recordsCount > productsCount) {
                currentPage = 1;
            }
            numberOfPages = productsCount / recordsCount;
            if (numberOfPages % recordsCount > 0) {
                numberOfPages++;
            }
            category = getCategoryWithLimitedProductList(categoryId, currentPage, recordsCount);
        }
        modelAndView.addObject(CATEGORY_PARAM, category);
        modelAndView.addObject(PRODUCT_LIST_CURRENT_PAGE, currentPage);
        modelAndView.addObject(NUMBER_OF_PRODUCT_PAGES, numberOfPages);
        modelAndView.addObject(PRODUCT_RECORDS_PER_PAGE, recordsPerPage);
        modelAndView.addObject(PRODUCT_RECORDS_ALL, productsCount);
        modelAndView.setViewName(CATEGORY_PAGE);
        return modelAndView;
    }
}
