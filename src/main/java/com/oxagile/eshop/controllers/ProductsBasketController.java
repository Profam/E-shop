package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.service.OrderService;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.BASKET_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.HOME_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SIGN_IN_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_TOTAL_PRICE_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_ID_PARAM;

@Controller
@SessionAttributes(PRODUCT_LIST_ATTRIBUTE)
@RequestMapping("/basket")
public class ProductsBasketController {
    private static final Logger LOG = LogManager.getLogger(ProductsBasketController.class);

    private final ProductService productService;

    private final OrderService orderService;

    public ProductsBasketController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @ModelAttribute(PRODUCT_LIST_ATTRIBUTE)
    public List<Product> initProductList() {
        return new ArrayList<>();
    }

    @GetMapping
    public String showBasketPage() {
        return BASKET_PAGE;
    }

    @PostMapping("/products/{productId}/add")
    public ModelAndView addProductToBasket(
            @PathVariable(PRODUCT_ID_PARAM) int productId,
            @ModelAttribute(PRODUCT_LIST_ATTRIBUTE) List<Product> products,
            ModelAndView modelAndView) {
        LOG.info("Call addReceivedProductToBasket method to add product in basket...");
        try {
            productService.addProductToBasket(productId, products);
            modelAndView.setViewName(BASKET_PAGE);
        } catch (Exception e) {
            LOG.debug("Failed to add product in basket!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }

    @GetMapping("/products/{productId}/remove")
    public ModelAndView deleteProductFromBasketById(
            @PathVariable(PRODUCT_ID_PARAM) int productId,
            @ModelAttribute(PRODUCT_LIST_ATTRIBUTE) List<Product> products,
            ModelAndView modelAndView) {
        LOG.info("Call deleteProductById method to delete product from basket by id...");
        try {
            productService.removeProductFromBasket(productId, products);
            modelAndView.setViewName(BASKET_PAGE);
        } catch (Exception e) {
            LOG.info("Failed to delete product from basket!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }

    @PostMapping("/products/order/save")
    public ModelAndView saveUserOrder(
            @RequestParam(USER_ID_PARAM) String userId,
            @RequestParam(ORDER_TOTAL_PRICE_PARAM) String totalPrice,
            @ModelAttribute(PRODUCT_LIST_ATTRIBUTE) List<Product> products,
            ModelAndView modelAndView,
            SessionStatus sessionStatus) {
        LOG.info("Call saveUserOrder method to try to save new User's order...");
        try {
            if (orderService.isNotAuthorized(userId)) {
                modelAndView.setViewName(SIGN_IN_PAGE);
            } else {
                orderService.createOrder(userId, totalPrice);
                sessionStatus.setComplete();
                modelAndView.setViewName(HOME_PAGE);
            }
        } catch (Exception e) {
            LOG.debug("Failed to save user's order info!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
