package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;

import java.sql.Date;

public interface UserService extends BaseService<User> {
    boolean validateLoginData(String email) throws ServiceException;

    boolean validateBirthday(Date birthday) throws ServiceException;

    boolean validateUserCredits(User user, String confirmEmail) throws ServiceException;

    void createNewUser(User user) throws ServiceException;

    User findByUserEmail(String email) throws ServiceException;
}
