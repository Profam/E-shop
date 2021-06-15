package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.daoimpl.UserDAOImpl;
import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_AGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_EMAIL_REGEX;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAOImpl userDAOImpl;

    public UserServiceImpl(UserDAOImpl userDAOImpl) {
        this.userDAOImpl = userDAOImpl;
    }

    @Override
    public User save(User entity) throws ServiceException {
        log.info("Try to save user... : " + entity.toString());
        try {
            return userDAOImpl.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save user!", e);
        }
    }

    @Override
    public User getById(int id) throws ServiceException {
        log.info("Try to find user... : " + id);
        try {
            return userDAOImpl.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find user!", e);
        }
    }

    @Override
    public User getUserByCredits(String login, String pass) throws ServiceException {
        log.info("Try to find user by loginData... : ");
        User user;
        try {
            user = userDAOImpl.getUserByEmail(login);
            if (validateLoginData(user, login, pass)) {
                return user;
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find user by login!", e);
        }
        return null;
    }

    @Override
    public List<User> getAll() throws ServiceException {
        log.info("Try to find users...");
        try {
            return userDAOImpl.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any user!", e);
        }
    }

    @Override
    public void update(User entity) throws ServiceException {
        log.info("Try to update user... " + entity.toString());
        try {
            userDAOImpl.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update user!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("Try to delete user... " + id);
        try {
            userDAOImpl.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete user!", e);
        }
    }

    public boolean validateLoginData(User user, String email, String password) throws ServiceException {
        log.info("Try to validate login data... :");
        try {
            return (Pattern.compile(VALID_EMAIL_REGEX).matcher(email).matches() &&
                    user.getEmail().equals(email) && user.getPassword().equals(password));
        } catch (Exception e) {
            throw new ServiceException("Failed to validate user data!", e);
        }
    }

    public boolean validateBirthday(Date birthday) throws ServiceException {
        log.info("Try to validate birthday date... :");
        try {
            return (LocalDate.now().minusYears(VALID_AGE).isAfter(birthday.toLocalDate()));
        } catch (Exception e) {
            throw new ServiceException("Failed to validate birthday date!", e);
        }
    }
}