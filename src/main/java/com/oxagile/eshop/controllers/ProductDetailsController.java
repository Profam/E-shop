package com.oxagile.eshop.controllers;

import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PRODUCT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_ID_PARAM;

@Controller
public class ProductDetailsController {
    private static final Logger LOG = LogManager.getLogger(ProductDetailsController.class);

    private final ProductService productService;

    public ProductDetailsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{productId}")
    public ModelAndView showProductDetails(
            @PathVariable(PRODUCT_ID_PARAM) int productId,
            ModelAndView modelAndView) {
        LOG.info("Call showProductDetails method to show the details of the product...");
        try {
            modelAndView.addObject(PRODUCT_ATTRIBUTE, productService.getById(productId));
            modelAndView.setViewName(PRODUCT_PAGE);
        } catch (Exception e) {
            LOG.info("Failed to show product!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
