package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.User;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

public interface UserDAO extends BaseDAO<User> {
    User getUserByEmail(String email) throws ConnectionPoolException, DaoException;
}
