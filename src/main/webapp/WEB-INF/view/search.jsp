<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<div class="container-fluid">
    <form action="${myContext}/search" method="get">
        <div class="d-flex" id="search-string">
            <input class="form-control me-2" type="search" name="search" value="${search}" placeholder="Поиск"
                   aria-label="Поиск">
            <button class="btn btn btn-outline" type="submit">Поиск</button>
        </div>

        <div class="d-flex">
            <label class="d-flex justify-content-center align-items-center w-75"><h5>Фильтрация данных</h5></label>
            <c:if test="${not empty categories}">
                <select class="custom- form-control w-100" id="exampleFormControlSelect2" name="categoryId">
                    <option value="" style="font-size: 20px" selected>Выберите категорию...</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.name}" style="font-size: 20px">${category.name}</option>
                    </c:forEach>
                </select>
            </c:if>

            <input type="number" id="priceFrom" name="priceFrom" value="${priceFrom}" min="1"
                   class="form-control m-1 w-50" placeholder="Цена от">

            <input type="number" id="priceTo" name="priceTo" value="${priceTo}" min="1" class="form-control m-1 w-50"
                   placeholder="Цена до">

            <button type="submit" class="btn btn-outline" name="command" value="search">Применить</button>
        </div>
    </form>
</div>

<div id="recordsDisplayCount" class="center-block w-25 m-auto">
    <span id="recordsCount">Показывать по:</span>
    <a id="pagesize_10 active"
       href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=10&currentPage=1">10</a>
    <a id="pagesize_20"
       href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=20&currentPage=1">20</a>
    <a id="pagesize_50"
       href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=50&currentPage=1">50</a>
    <a id="pagesize_all"
       href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=all&currentPage=1">Все</a>
</div>

</br>

<div class="row w-50 m-auto">
    <div class="gallery_product col-9">
            <c:if test="${not empty products}">
                <div type="productLinks">
                    <c:forEach items="${products}" var="product">
                        <div class="row">
                            <div class="col-6">
                                <a id="productRedirectLink"
                                   href="${myContext}/products/${product.id}">
                                    <c:forEach items="${product.images}" var="image">
                                        <img class="img-fluid"
                                             src="${myContext}${image.imagePath}" style="padding: 5px;"
                                             alt="No image"/>
                                    </c:forEach>
                                </a>
                            </div>
                            <div class="col-6">
                                <p>Название товара:
                                    <a href="${myContext}/products/${product.id}">
                                            ${product.name}
                                    </a>
                                </p>
                                <p>Описание: ${product.description}</p>
                                <p>Цена: ${product.price}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
    </div>

    <nav aria-label="Navigation for search products" class="center-block w-50 m-auto">
        <div class="center-block w-50 m-auto">
            <ul class="pagination">
                <c:if test="${currentPage != 1 and currentPage > 0}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">
                            Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${numberOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link">
                                        ${i}
                                    <span class="sr-only">(current)</span>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt numberOfPages}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${myContext}/search?search=${search}&categoryId=${categoryId}&priceFrom=${priceFrom}&priceTo=${priceTo}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
</div>

<jsp:include page="footer.jsp"/>