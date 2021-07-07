package com.oxagile.eshop.controllers;

import com.oxagile.eshop.service.CategoryService;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORIES_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_FROM_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_TO_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_PER_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.SEARCH_REQUEST_PARAM;

@Controller
public class SearchController {
    private static final Logger LOG = LogManager.getLogger(SearchController.class);

    private final ProductService productService;
    private final CategoryService categoryService;

    public SearchController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    public ModelAndView showSearchResult(
            @RequestParam(value = SEARCH_REQUEST_PARAM, required = false) String searchParam,
            @RequestParam(value = CATEGORY_ID_PARAM, required = false) String categoryIdParam,
            @RequestParam(value = PRODUCT_PRICE_FROM_ATTRIBUTE, required = false) String priceFromParam,
            @RequestParam(value = PRODUCT_PRICE_TO_ATTRIBUTE, required = false) String priceToParam,
            @RequestParam(value = PRODUCT_RECORDS_PER_PAGE, required = false) String recordsPerPageParam,
            @RequestParam(value = PRODUCT_LIST_CURRENT_PAGE, required = false) String currentPageParam,
            ModelAndView modelAndView) {
        LOG.info("Call showSearchResult method to show products by search request parameters");
        try {
            productService.getSearchResult(modelAndView, searchParam, categoryIdParam,
                    priceFromParam, priceToParam, recordsPerPageParam, currentPageParam);
            modelAndView.addObject(CATEGORIES_LIST_ATTRIBUTE, categoryService.getAll());
        } catch (Exception e) {
            LOG.info("Failed to show product!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
