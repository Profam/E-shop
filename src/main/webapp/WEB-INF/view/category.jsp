<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<div class="mx-auto" style="width: 200px;">
    <h5>
        ${category.name}
    </h5>
</div>

<div id="recordsDisplayCount" class="center-block w-25 m-auto">
    <span id="recordsCount">Показывать по:</span>
    <a id="pagesize_10 active"
       href="${myContext}/categories/${category.id}?recordsPerPage=10&currentPage=1">10</a>
    <a id="pagesize_20"
       href="${myContext}/categories/${category.id}?recordsPerPage=20&currentPage=1">20</a>
    <a id="pagesize_50"
       href="${myContext}/categories/${category.id}?recordsPerPage=50&currentPage=1">50</a>
    <a id="pagesize_all"
       href="${myContext}/categories/${category.id}?recordsPerPage=all&currentPage=1">Все</a>
</div>
</br>

<c:if test="${not empty category}">
    <div type="productLinks" class="center-block w-50 m-auto">
        <c:forEach items="${category.productList}" var="product">
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

<nav aria-label="Navigation for search products" class="center-block w-50 m-auto">
    <div class="center-block w-50 m-auto">
        <ul class="pagination">
            <c:if test="${currentPage != 1 and currentPage > 0}">
                <li class="page-item">
                    <a class="page-link"
                       href="${myContext}/categories/${category.id}?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">
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
                               href="${myContext}/categories/${category.id}?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt numberOfPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="${myContext}/categories/${category.id}?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>

<jsp:include page="footer.jsp"/>