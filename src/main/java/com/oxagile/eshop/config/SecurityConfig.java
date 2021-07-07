package com.oxagile.eshop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_EMAIL_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.USER_PASSWORD_PARAM;

@Configuration
@EnableWebSecurity
@ComponentScan("com.oxagile.eshop")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("authService")
    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("authService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/").permitAll()
                .anyRequest().permitAll()
                .and();

        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter(USER_EMAIL_PARAM)
                .passwordParameter(USER_PASSWORD_PARAM)
                .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home?logout")
                .invalidateHttpSession(true);
    }
}
