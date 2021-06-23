package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.HOME_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SIGN_IN_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.INCORRECT_LOGIN_MESSAGE_VALUE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_EMAIL_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_PASSWORD_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_SESSION_PARAM;

@Controller
@SessionAttributes(USER_SESSION_PARAM)
public class UserSessionController {
    private static final Logger LOG = LogManager.getLogger(UserSessionController.class);

    private final UserService userService;

    public UserSessionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return SIGN_IN_PAGE;
    }

    @GetMapping("/logout")
    public String signOutUser(SessionStatus sessionStatus) {
        LOG.info("Call logout method to try to log out the user...");
        sessionStatus.setComplete();
        return "redirect: " + HOME_PAGE;
    }

    @PostMapping("/login")
    public ModelAndView signInUser(
            @RequestParam(USER_EMAIL_PARAM) String login,
            @RequestParam(USER_PASSWORD_PARAM) String password,
            ModelAndView modelAndView) {
        LOG.info("Call signIn method to try to log in system by user's credits...");
        User user;
        try {
            user = userService.getUserByCredits(login, password);
            if(user != null) {
                modelAndView.addObject(USER_SESSION_PARAM, user);
                modelAndView.setViewName("redirect:" + HOME_PAGE);
            } else {
                modelAndView.addObject(ERROR_NAME_ATTRIBUTE, INCORRECT_LOGIN_MESSAGE_VALUE);
                modelAndView.setViewName(PAGE_ERROR);
            }
        } catch (Exception e) {
            LOG.info("Failed to complete Sign In!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}