package com.oxagile.eshop.dao;

import com.oxagile.eshop.domain.ProductImage;
import com.oxagile.eshop.exceptions.ConnectionPoolException;
import com.oxagile.eshop.exceptions.DaoException;

import java.util.List;

public interface ProductImageDAO extends BaseDAO<ProductImage>{
    List<ProductImage> getImagesByProductId(int productId)throws ConnectionPoolException, DaoException;
}
