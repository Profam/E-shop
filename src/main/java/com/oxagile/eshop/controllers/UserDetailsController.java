package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.service.serviceimpl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SIGN_IN_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.USER_DETAILS_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;

@Controller
public class UserDetailsController {
    private static final Logger LOG = LogManager.getLogger(UserDetailsController.class);

    private final OrderServiceImpl orderService;

    public UserDetailsController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/details")
    public ModelAndView showUserDetailsPage(
            @RequestParam(value = USER_ID_PARAM, required = false) String userId,
            ModelAndView modelAndView) {
        LOG.debug("Call method showUserDetailsPage to show a user details...");
        try {
        if (userId.isEmpty()) {
            modelAndView.setViewName(SIGN_IN_PAGE);
        } else {
            List<Order> orderList = orderService.getOrdersWithProductsByUserId(Integer.parseInt(userId));
            if (orderList != null) {
                modelAndView.addObject(ORDER_LIST_ATTRIBUTE, orderList);
            }
            modelAndView.setViewName(USER_DETAILS_PAGE);
        }
        } catch (Exception e) {
            LOG.debug("Failed to show user details!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
