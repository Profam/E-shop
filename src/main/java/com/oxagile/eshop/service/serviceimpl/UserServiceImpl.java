package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.UserDAO;
import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_AGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.VALID_EMAIL_REGEX;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;


    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;

    }
    @Transactional
    @Override
    public User save(User entity) throws ServiceException {
        LOG.info("Try to save user: {}", entity);
        try {
            return userDAO.create(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save user!", e);
        }
    }
    @Transactional
    @Override
    public User getById(int id) throws ServiceException {
        LOG.info("Try to find user, id: {}", id);
        try {
            return userDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find user!", e);
        }
    }
    @Transactional
    @Override
    public List<User> getAll() throws ServiceException {
        LOG.info("Try to find users...");
        try {
            return userDAO.readAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any user!", e);
        }
    }
    @Transactional
    @Override
    public void update(User entity) throws ServiceException {
        LOG.info("Try to update user: {}", entity);
        try {
            userDAO.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update user!", e);
        }
    }
    @Transactional
    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete user, id: {}", id);
        try {
            userDAO.delete(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete user!", e);
        }
    }
    @Transactional
    @Override
    public User getUserByCredits(String login, String pass) throws ServiceException {
        LOG.info("Try to find user by loginData: {} {}", login, pass);
        User user;
        try {
            user = userDAO.getUserByEmail(login);
            if (validateLoginData(user, login, pass)) {
                return user;
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find user by login!", e);
        }
        return null;
    }

    public boolean validateLoginData(User user, String email, String password) throws ServiceException {
        LOG.info("Try to validate login data: {} {} {}", user, email, password);
        try {
            return (Pattern.compile(VALID_EMAIL_REGEX).matcher(email).matches() &&
                    user.getEmail().equals(email) && user.getPassword().equals(password));
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
    @Transactional
    @Override
    public boolean validateUserCredits(User user, String confirmEmail) throws ServiceException {
        return (validateBirthday(user.getBirthday()) && user.getEmail().equals(confirmEmail));
    }
    @Transactional
    @Override
    public void createNewUser(User user) throws ServiceException {
        save(user);
    }
@Transactional
    @Override
    public User findByUserEmail(String email) throws ServiceException {
        LOG.info("Try to find user by email, email: {}", email);
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Failed to find user!", e);
        }
    }

}