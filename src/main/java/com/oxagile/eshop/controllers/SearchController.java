package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.CategoryService;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SEARCH_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORIES_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.DEFAULT_RECORDS_COUNT_PER_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.NUMBER_OF_PRODUCT_PAGES;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_FROM_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_TO_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_ALL;
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
        Optional<String> searchRequestKey = Optional.ofNullable(searchParam);
        Optional<String> categoryIdKey = Optional.ofNullable(categoryIdParam);
        Optional<String> priceFromKey = Optional.ofNullable(priceFromParam);
        Optional<String> priceToKey = Optional.ofNullable(priceToParam);
        String recordsPerPage = recordsPerPageParam;
        int currentPage = 1;
        int numberOfPages = 1;
        Map<String, String> searchParams;
        List<Product> products;
        int productsCount;

        try {
            if (recordsPerPage == null) {
                recordsPerPage = DEFAULT_RECORDS_COUNT_PER_PAGE;
            }
            searchParams = fillMapOfSearchParameters(modelAndView, searchRequestKey, categoryIdKey, priceFromKey, priceToKey);
            products = productService.getProductsListByParams(searchParams, 1, 0);
            productsCount = products.size();


            if (recordsPerPage.equals("all")) {
                if (productsCount == 0) {
                    productsCount = Integer.parseInt(DEFAULT_RECORDS_COUNT_PER_PAGE);
                }
                fillResponse(modelAndView, searchParams, numberOfPages, currentPage, productsCount);
            } else {
                if (currentPageParam != null) {
                    currentPage = Integer.parseInt(currentPageParam);
                }

                int recordsCount = Integer.parseInt(recordsPerPage);

                numberOfPages = productsCount / recordsCount;

                if (numberOfPages % recordsCount > 0) {
                    numberOfPages++;
                }

                if (recordsCount >= productsCount || currentPage > numberOfPages) {
                    currentPage = 1;
                    numberOfPages = 1;
                }

                fillResponse(modelAndView, searchParams, numberOfPages, currentPage, recordsCount);
                modelAndView.addObject(PRODUCT_RECORDS_PER_PAGE, recordsPerPage);
            }
            modelAndView.addObject(CATEGORIES_LIST_ATTRIBUTE, categoryService.getAll());
            modelAndView.addObject(PRODUCT_RECORDS_ALL, productsCount);
            modelAndView.setViewName(SEARCH_PAGE);
        } catch (Exception e) {
            LOG.info("Failed to show product!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }

    private Map<String, String> fillMapOfSearchParameters(
            ModelAndView modelAndView,
            Optional<String> searchRequestKey,
            Optional<String> categoryIdKey,
            Optional<String> priceFromKey,
            Optional<String> priceToKey) {
        Map<String, String> searchParameters = new HashMap<>();

        if (searchRequestKey.isPresent() && !searchRequestKey.get().isEmpty()) {
            searchParameters.put(SEARCH_REQUEST_PARAM, searchRequestKey.get());
        }

        if (categoryIdKey.isPresent() && !categoryIdKey.get().isEmpty()) {
            searchParameters.put(CATEGORY_ID_PARAM, categoryIdKey.get());
            modelAndView.addObject(CATEGORY_ID_PARAM, categoryIdKey.get());
        } else {
            modelAndView.addObject(CATEGORY_ID_PARAM, "");
        }

        if (priceFromKey.isPresent() && !priceFromKey.get().isEmpty()) {
            searchParameters.put(PRODUCT_PRICE_FROM_ATTRIBUTE, priceFromKey.get());
            modelAndView.addObject(PRODUCT_PRICE_FROM_ATTRIBUTE, priceFromKey.get());
        } else {
            modelAndView.addObject(PRODUCT_PRICE_FROM_ATTRIBUTE, "");
        }

        if (priceToKey.isPresent() && !priceToKey.get().isEmpty()) {
            searchParameters.put(PRODUCT_PRICE_TO_ATTRIBUTE, priceToKey.get());
            modelAndView.addObject(PRODUCT_PRICE_TO_ATTRIBUTE, priceToKey.get());
        } else {
            modelAndView.addObject(PRODUCT_PRICE_TO_ATTRIBUTE, "");
        }
        return searchParameters;
    }

    private void fillResponse(ModelAndView modelAndView, Map<String, String> searchParams, int numberOfPages,
                              int currentPage, int recordsCount) throws ServiceException {
        List<Product> products = productService.getProductsListByParams(searchParams, currentPage, recordsCount);
        modelAndView.addObject(PRODUCT_LIST_ATTRIBUTE, products);
        modelAndView.addObject(SEARCH_REQUEST_PARAM, searchParams.get(SEARCH_REQUEST_PARAM));
        modelAndView.addObject(PRODUCT_LIST_CURRENT_PAGE, currentPage);
        modelAndView.addObject(NUMBER_OF_PRODUCT_PAGES, numberOfPages);
    }
}
