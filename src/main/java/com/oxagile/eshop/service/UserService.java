package com.oxagile.eshop.service;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ServiceException;

public interface UserService extends BaseService<User>{
    User getUserByCredits(String login, String pass) throws ServiceException;
}
