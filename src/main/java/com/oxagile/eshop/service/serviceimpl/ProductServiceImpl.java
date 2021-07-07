package com.oxagile.eshop.service.serviceimpl;

import com.oxagile.eshop.dao.ProductDAO;
import com.oxagile.eshop.dao.specifications.ProductSpecification;
import com.oxagile.eshop.domain.Product;
import com.oxagile.eshop.exceptions.ServiceException;
import com.oxagile.eshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oxagile.eshop.controllers.pools.PagesPathesPool.SEARCH_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.CATEGORY_ID_PARAM;
import static com.oxagile.eshop.controllers.pools.ParamsPool.DEFAULT_RECORDS_COUNT_PER_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.NUMBER_OF_PRODUCT_PAGES;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_LIST_CURRENT_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_FROM_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_PRICE_TO_ATTRIBUTE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_ALL;
import static com.oxagile.eshop.controllers.pools.ParamsPool.PRODUCT_RECORDS_PER_PAGE;
import static com.oxagile.eshop.controllers.pools.ParamsPool.SEARCH_REQUEST_PARAM;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Transactional
    @Override
    public Product save(Product entity) throws ServiceException {
        LOG.info("Try to save product: {}", entity);
        try {
            return productDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save product!", e);
        }
    }

    @Override
    public Product getById(int id) throws ServiceException {
        LOG.info("Try to find product, id: {}", id);
        try {
            Optional<Product> productOptional = productDAO.findById(id);
            if (productOptional.isPresent()) {
                return productOptional.get();
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find product by id!", e);
        }
        return new Product();
    }

    @Override
    public List<Product> getAll() throws ServiceException {
        LOG.info("Try to find products...");
        try {
            return (List<Product>) productDAO.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to find any product!", e);
        }
    }

    @Override
    public List<Product> getProductsListByParams
            (Map<String, String> parameters, int currentPage, int recordsCount) throws ServiceException {
        LOG.info("Try to find products by parameters: {}", parameters);
        try {
            Specification<Product> specifications = Specification
                    .where(ProductSpecification.joinCategoriesWhere(parameters.get(CATEGORY_ID_PARAM)))
                    .and(ProductSpecification.likeProductsName(parameters.get(SEARCH_REQUEST_PARAM))
                            .or(ProductSpecification.likeProductsDescription(parameters.get(SEARCH_REQUEST_PARAM))))
                    .and(ProductSpecification.priceFromGreaterThan(parameters.get(PRODUCT_PRICE_FROM_ATTRIBUTE)))
                    .and(ProductSpecification.priceToLessThan(parameters.get(PRODUCT_PRICE_TO_ATTRIBUTE)));
            if (!(currentPage == 1 && recordsCount == 0)) {
                Pageable paging = PageRequest.of(currentPage - 1, recordsCount);
                Page<Product> limitedProductList = productDAO.findAll(specifications, paging);
                return limitedProductList.getContent();
            } else {
                return productDAO.findAll(specifications);
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find  products by parameters!", e);
        }
    }

    @Override
    public void update(Product entity) throws ServiceException {
        LOG.info("Try to update product: {}", entity);
        try {
            productDAO.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update product!", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOG.info("Try to delete product, id: {}", id);
        try {
            if (productDAO.existsById(id)) {
                productDAO.deleteById(id);
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to delete product!", e);
        }
    }

    @Override
    public List<Product> addProductToBasket(int productId, List<Product> products) throws ServiceException {
        Product product = getById((productId));
        if (!products.contains(product)) {
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> removeProductFromBasket(int productId, List<Product> products) throws ServiceException {
        Product product = getById(productId);
        products.removeIf(productInList -> productInList.getId() == product.getId());
        return products;
    }

    @Override
    public ModelAndView getSearchResult(ModelAndView modelAndView, String searchParam,
                                        String categoryIdParam, String priceFromParam,
                                        String priceToParam, String recordsPerPageParam,
                                        String currentPageParam) throws ServiceException {
        Optional<String> searchRequestKey = Optional.ofNullable(searchParam);
        Optional<String> categoryIdKey = Optional.ofNullable(categoryIdParam);
        Optional<String> priceFromKey = Optional.ofNullable(priceFromParam);
        Optional<String> priceToKey = Optional.ofNullable(priceToParam);
        String recordsPerPage = recordsPerPageParam;
        int currentPage = 1;
        int numberOfPages = 1;
        Map<String, String> searchParams;
        List<Product> products;
        int productsCount;
        if (recordsPerPage == null) {
            recordsPerPage = DEFAULT_RECORDS_COUNT_PER_PAGE;
        }
        searchParams = fillMapOfSearchParameters(modelAndView, searchRequestKey, categoryIdKey, priceFromKey, priceToKey);
        products = getProductsListByParams(searchParams, 1, 0);
        productsCount = products.size();
        if (recordsPerPage.equals("all")) {
            if (productsCount == 0) {
                productsCount = Integer.parseInt(DEFAULT_RECORDS_COUNT_PER_PAGE);
            }
            fillResponse(modelAndView, searchParams, numberOfPages, currentPage, productsCount);
        } else {
            if (currentPageParam != null) {
                currentPage = Integer.parseInt(currentPageParam);
            }

            int recordsCount = Integer.parseInt(recordsPerPage);

            numberOfPages = productsCount / recordsCount;

            if (numberOfPages % recordsCount > 0) {
                numberOfPages++;
            }

            if (recordsCount >= productsCount || currentPage > numberOfPages) {
                currentPage = 1;
                numberOfPages = 1;
            }

            fillResponse(modelAndView, searchParams, numberOfPages, currentPage, recordsCount);
            modelAndView.addObject(PRODUCT_RECORDS_PER_PAGE, recordsPerPage);
        }
        modelAndView.addObject(PRODUCT_RECORDS_ALL, productsCount);
        modelAndView.setViewName(SEARCH_PAGE);
        return modelAndView;
    }

    private Map<String, String> fillMapOfSearchParameters(
            ModelAndView modelAndView,
            Optional<String> searchRequestKey,
            Optional<String> categoryIdKey,
            Optional<String> priceFromKey,
            Optional<String> priceToKey) {
        Map<String, String> searchParameters = new HashMap<>();

        if (searchRequestKey.isPresent() && !searchRequestKey.get().isEmpty()) {
            searchParameters.put(SEARCH_REQUEST_PARAM, searchRequestKey.get());
        }

        if (categoryIdKey.isPresent() && !categoryIdKey.get().isEmpty()) {
            searchParameters.put(CATEGORY_ID_PARAM, categoryIdKey.get());
            modelAndView.addObject(CATEGORY_ID_PARAM, categoryIdKey.get());
        } else {
            modelAndView.addObject(CATEGORY_ID_PARAM, "");
        }

        if (priceFromKey.isPresent() && !priceFromKey.get().isEmpty()) {
            searchParameters.put(PRODUCT_PRICE_FROM_ATTRIBUTE, priceFromKey.get());
            modelAndView.addObject(PRODUCT_PRICE_FROM_ATTRIBUTE, priceFromKey.get());
        } else {
            modelAndView.addObject(PRODUCT_PRICE_FROM_ATTRIBUTE, "");
        }

        if (priceToKey.isPresent() && !priceToKey.get().isEmpty()) {
            searchParameters.put(PRODUCT_PRICE_TO_ATTRIBUTE, priceToKey.get());
            modelAndView.addObject(PRODUCT_PRICE_TO_ATTRIBUTE, priceToKey.get());
        } else {
            modelAndView.addObject(PRODUCT_PRICE_TO_ATTRIBUTE, "");
        }
        return searchParameters;
    }

    private void fillResponse(ModelAndView modelAndView, Map<String, String> searchParams, int numberOfPages,
                              int currentPage, int recordsCount) throws ServiceException {
        List<Product> products = getProductsListByParams(searchParams, currentPage, recordsCount);
        modelAndView.addObject(PRODUCT_LIST_ATTRIBUTE, products);
        modelAndView.addObject(SEARCH_REQUEST_PARAM, searchParams.get(SEARCH_REQUEST_PARAM));
        modelAndView.addObject(PRODUCT_LIST_CURRENT_PAGE, currentPage);
        modelAndView.addObject(NUMBER_OF_PRODUCT_PAGES, numberOfPages);
    }
}