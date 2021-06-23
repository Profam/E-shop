package com.oxagile.eshop.web;

import com.oxagile.eshop.config.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    protected SecurityInitializer() {
        super(SecurityConfig.class);
    }
}
