package com.oxagile.eshop.controllers.pools;

public interface ParamsPool {
    String USER_ID_PARAM = "userId";
    String USER_NAME_PARAM = "name";
    String USER_SURNAME_PARAM = "surname";
    String USER_EMAIL_PARAM = "email";
    String USER_SESSION_PARAM = "userDetails";
    String USER_CONFIRM_EMAIL_PARAM = "confirmEmail";
    String USER_PASSWORD_PARAM = "password";
    String USER_BIRTHDAY_PARAM = "birthday";
    String CATEGORY_ID_PARAM = "categoryId";
    String CATEGORY_PARAM = "category";
    String CATEGORIES_LIST_ATTRIBUTE = "categories";
    String PRODUCT_ID_PARAM = "productId";
    String PRODUCT_ATTRIBUTE = "product";
    String PRODUCT_RECORDS_PER_PAGE = "recordsPerPage";
    String PRODUCT_RECORDS_ALL = "productsCount";
    String PRODUCT_PRICE_TO_ATTRIBUTE = "priceTo";
    String PRODUCT_PRICE_FROM_ATTRIBUTE = "priceFrom";
    String PRODUCT_LIST_ATTRIBUTE = "products";
    String PRODUCT_LIST_CURRENT_PAGE = "currentPage";
    String PRODUCT_IMAGES_ATTRIBUTE = "productImages";
    String ORDER_PRICE_PARAM = "price";
    String ORDER_TOTAL_PRICE_PARAM = "totalPrice";
    String ORDER_LIST_ATTRIBUTE = "orders";
    String DEFAULT_ROLE = "admin";
    String INCORRECT_LOGIN_MESSAGE_VALUE = "message_incorrect_login_password";
    String NUMBER_OF_PRODUCT_PAGES = "numberOfPages";
    String ERROR_NAME_ATTRIBUTE = "errorMessage";
    String DEFAULT_RECORDS_COUNT_PER_PAGE = "10";
    String SEARCH_REQUEST_PARAM = "search";
    String VALID_EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    int VALID_AGE = 16;
}