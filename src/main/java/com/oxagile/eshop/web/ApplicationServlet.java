package com.oxagile.eshop.web;

import com.oxagile.eshop.config.JpaConfig;
import com.oxagile.eshop.config.SecurityConfig;
import com.oxagile.eshop.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationServlet implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan("com.oxagile.eshop");
       /* root.register(WebConfig.class);
        root.register(JpaConfig.class);
        root.register(SecurityConfig.class);*/
        servletContext.addListener(new ContextLoaderListener(root));

        ServletRegistration.Dynamic appDispatcher =
                servletContext.addServlet("SpringDispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        appDispatcher.setLoadOnStartup(1);
        appDispatcher.addMapping("/");

        FilterRegistration.Dynamic filterRegistration =
                servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, true, "/");
    }
}