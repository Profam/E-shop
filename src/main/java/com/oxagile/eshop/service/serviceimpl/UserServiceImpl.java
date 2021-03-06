package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.UserDAO;
import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_AGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_EMAIL_REGEX;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;

        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User save(User entity) throws ServiceException {
        LOG.info("Try to save user: {}", entity);
        try {
            return userDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save user!", e);
        }
    }

    @Override
    public User getById(int id) throws ServiceException {
        LOG.info("Try to find user, id: {}", id);
        try {
            Optional<User> userOptional = userDAO.findById(id);
            if (userOptional.isPresent()) {
                return userOptional.get();
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find user!", e);
        }
        return new User();
    }

    @Override
    public List<User> getAll() throws ServiceException {
        LOG.info("Try to find users...");
        try {
            return (List<User>) userDAO.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any user!", e);
        }
    }

    @Override
    public void update(User entity) throws ServiceException {
        LOG.info("Try to update user: {}", entity);
        try {
            userDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update user!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete user, id: {}", id);
        try {
            if (userDAO.existsById(id)) {
                userDAO.deleteById(id);
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to delete user!", e);
        }
    }

    public boolean validateLoginData(String email) throws ServiceException {
        LOG.info("Try to validate login data: {}", email);
        try {
            return (Pattern.compile(VALID_EMAIL_REGEX).matcher(email).matches());
        } catch (Exception e) {
            throw new ServiceException("Failed to validate user data!", e);
        }
    }

    public boolean validateBirthday(Date birthday) throws ServiceException {
        LOG.info("Try to validate birthday date: {}", birthday);
        try {
            return (LocalDate.now().minusYears(VALID_AGE).isAfter(birthday.toLocalDate()));
        } catch (Exception e) {
            throw new ServiceException("Failed to validate birthday date!", e);
        }
    }

    @Override
    public boolean validateUserCredits(User user, String confirmEmail) throws ServiceException {
        return (validateBirthday(user.getBirthday()) && user.getEmail().equals(confirmEmail));
    }

    @Transactional
    @Override
    public void createNewUser(User user) throws ServiceException {
        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        save(user);
    }

    @Transactional
    @Override
    public User findByUserEmail(String email) throws ServiceException {
        LOG.info("Try to find user by email, email: {}", email);
        try {
            return userDAO.findUserByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Failed to find user!", e);
        }
    }
}