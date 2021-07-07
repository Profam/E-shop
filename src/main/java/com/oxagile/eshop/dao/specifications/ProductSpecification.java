package com.oxagile.eshop.dao.specifications;

import com.oxagile.eshop.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_NAME_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_DESCRIPTION_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_NAME_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_PARAM;

public class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> joinCategoriesWhere(String categoryId) {
        return (root, query, cb) -> categoryId == null ? null :
                cb.equal(root.join(CATEGORY_PARAM).get(CATEGORY_NAME_PARAM), categoryId);
    }

    public static Specification<Product> likeProductsName(String search) {
        return (root, query, cb) -> search == null ? null :
                cb.like(root.get(PRODUCT_NAME_PARAM), "%" + search + "%");
    }

    public static Specification<Product> likeProductsDescription(String search) {
        return (root, query, cb) -> search == null ? null :
                cb.like(root.get(PRODUCT_DESCRIPTION_PARAM), "%" + search + "%");
    }

    public static Specification<Product> priceFromGreaterThan(String priceFrom) {
        return (root, query, cb) -> priceFrom == null ? null :
                (cb.ge(root.get(PRODUCT_PRICE_PARAM), Integer.parseInt(priceFrom)));
    }

    public static Specification<Product> priceToLessThan(String priceTo) {
        return (root, query, cb) -> priceTo == null ? null :
                (cb.le(root.get(PRODUCT_PRICE_PARAM), Integer.parseInt(priceTo)));
    }
}




