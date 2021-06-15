package com.oxagile.eshop.controllers;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.service.serviceimpl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SIGN_IN_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SIGN_UP_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_CONFIRM_EMAIL_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_SESSION_PARAM;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private static final Logger LOG = LogManager.getLogger(RegisterController.class);

    private final UserServiceImpl userService;

    public RegisterController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView showRegisterPage() {
        return new ModelAndView(SIGN_UP_PAGE, USER_SESSION_PARAM, new User());
    }

    @PostMapping("/new")
    public ModelAndView registerNewUser(
            @ModelAttribute(USER_SESSION_PARAM) User user,
            @RequestParam(USER_CONFIRM_EMAIL_PARAM) String confirmEmail,
            BindingResult result, SessionStatus sessionStatus,
            ModelAndView modelAndView) {
        LOG.debug("Call method registerNewUser to try to register the user...");
        try {
            if (result.hasErrors()) {
                modelAndView.setViewName(PAGE_ERROR);
            }
            if (userService.validateBirthday(user.getBirthday()) &&
                    user.getEmail().equals(confirmEmail)) {
                userService.save(user);
                LOG.debug("Successful registration!");
                modelAndView.setViewName(SIGN_IN_PAGE);
                sessionStatus.setComplete();
            } else {
                modelAndView.setViewName(SIGN_UP_PAGE);
            }
        } catch (Exception e) {
            LOG.debug("Failed to register user, try again!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}

