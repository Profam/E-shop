package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;

import java.sql.Date;

public interface UserService extends BaseService<User> {
    User getUserByCredits(String login, String pass) throws ServiceException;

    boolean validateLoginData(User user, String email, String password) throws ServiceException;

    boolean validateBirthday(Date birthday) throws ServiceException;

    boolean validateUserCredits(User user, String confirmEmail) throws ServiceException;

    void createNewUser(User user) throws ServiceException;

    User findByUserEmail(String email) throws ServiceException;
}
