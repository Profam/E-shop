package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.Order;
import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.service.OrderService;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.USER_DETAILS_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ORDER_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_EMAIL_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_SESSION_PARAM;

@Controller
public class UserDetailsController {
    private static final Logger LOG = LogManager.getLogger(UserDetailsController.class);

    private final OrderService orderService;
    private final UserService userService;

    public UserDetailsController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/user/details")
    public ModelAndView showUserDetailsPage(
            @RequestParam(value = USER_EMAIL_PARAM, required = false) String email,
            ModelAndView modelAndView) {
        LOG.info("Call method showUserDetailsPage to show a user details...");
        try {
            User user = userService.findByUserEmail(email);
            List<Order> orderList = orderService.getOrdersWithProductsByUserId(user.getId());
            if (orderList != null) {
                modelAndView.addObject(ORDER_LIST_ATTRIBUTE, orderList);
            }
            modelAndView.addObject(USER_SESSION_PARAM, user);
            modelAndView.setViewName(USER_DETAILS_PAGE);
        } catch (Exception e) {
            LOG.info("Failed to show user details!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
