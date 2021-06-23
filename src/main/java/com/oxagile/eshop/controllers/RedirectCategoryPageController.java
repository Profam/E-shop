package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.Category;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.CategoryService;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.CATEGORY_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.NUMBER_OF_PRODUCT_PAGES;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_ALL;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_PER_PAGE;

@Controller
public class RedirectCategoryPageController {
    private static final Logger LOG = LogManager.getLogger(RedirectCategoryPageController.class);

    private final ProductService productService;

    private final CategoryService categoryService;

    public RedirectCategoryPageController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/{categoryId}")
    public ModelAndView showProductsInCategory(
            @PathVariable(CATEGORY_ID_PARAM) int categoryId,
            @RequestParam(PRODUCT_LIST_CURRENT_PAGE) int currentPage,
            @RequestParam(PRODUCT_RECORDS_PER_PAGE) String recordsPerPage,
            ModelAndView modelAndView) {
        LOG.debug("Call showProductsInCategory method to show the products of the category...");
        try {
            fillModelAndViewObjects(modelAndView,categoryId,currentPage,recordsPerPage);
        } catch (Exception e) {
            LOG.debug("Failed to show products!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }

    private ModelAndView fillModelAndViewObjects(ModelAndView modelAndView, int categoryId,
            int currentPage, String recordsPerPage) throws ServiceException {
        Category category;
        int numberOfPages = 0;
        int productsCount;
        productsCount = (int) productService.getCountOfProductsForCategory(categoryId);
        if (recordsPerPage.equals("all")) {
            category = categoryService.getCategoryWithLimitedProductList(categoryId, currentPage, productsCount);
        } else {
            int recordsCount = Integer.parseInt(recordsPerPage);
            if (recordsCount > productsCount) {
                currentPage = 1;
            }
            numberOfPages = productsCount / recordsCount;
            if (numberOfPages % recordsCount > 0) {
                numberOfPages++;
            }
            category = categoryService.getCategoryWithLimitedProductList(categoryId, currentPage, recordsCount);
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