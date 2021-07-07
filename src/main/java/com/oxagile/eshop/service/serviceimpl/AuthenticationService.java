package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.service.UserService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("authService")
public class AuthenticationService implements UserDetailsService {
    private static final Logger LOG = LogManager.getLogger(AuthenticationService.class);

    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) {
        LOG.info("Calling loadUserByUsername: {}", email);
        User user = null;
        if (userService.validateLoginData(email)) {
            user = userService.findByUserEmail(email);
        }
        if (user == null) throw new UsernameNotFoundException("User not found: " + email);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roles);
    }
}
