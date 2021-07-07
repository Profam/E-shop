package com.oxagile.eshop.controllers;

import com.oxagile.eshop.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_PER_PAGE;

@Controller
public class RedirectCategoryPageController {
    private static final Logger LOG = LogManager.getLogger(RedirectCategoryPageController.class);
    private final CategoryService categoryService;

    public RedirectCategoryPageController(CategoryService categoryService) {
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
            categoryService.getLimitedProductListFromCategory(modelAndView, categoryId, currentPage, recordsPerPage);
        } catch (Exception e) {
            LOG.debug("Failed to show products!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}