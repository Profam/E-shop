package com.oxagile.eshop.controllers;

import com.oxagile.eshop.service.serviceimpl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.HOME_PAGE;
import static com.oxagile.eshop.controllers.pools.PagesPathesPool.PAGE_ERROR;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORIES_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.ERROR_NAME_ATTRIBUTE;

@Controller
public class HomeController {
    private static final Logger LOG = LogManager.getLogger(HomeController.class);

    private final CategoryServiceImpl categoryService;

    public HomeController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(ModelAndView modelAndView) {
        LOG.debug("Call method homePage to show a list of categories...");
        try {
            modelAndView.addObject(CATEGORIES_LIST_ATTRIBUTE, categoryService.getAll());
            modelAndView.setViewName(HOME_PAGE);
        } catch (Exception e) {
            LOG.debug("Failed to show categories!");
            modelAndView.addObject(ERROR_NAME_ATTRIBUTE, e.getMessage());
            modelAndView.setViewName(PAGE_ERROR);
        }
        return modelAndView;
    }
}
