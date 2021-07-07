package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.User;

public interface UserDAO extends BaseDAO<User> {
    User findUserByEmail(String email);
}
