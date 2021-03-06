<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="header.jsp"/>
<c:set var="totalPrice" value="${0}" scope="session"/>
<c:set value="${sessionScope.products}" var="products"/>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<form action="${myContext}/basket/products/order/createOrder" method="post">
    <sec:authorize access="isAuthenticated()">
        <input type="hidden" name="email" value="<sec:authentication property="principal.username"/>"/>
    </sec:authorize>
    <input type="hidden" name="products" value="${products}"/>

    <c:if test="${not empty products}">
        <div type="productLinks" class="center-block w-50 m-auto">

            <c:forEach items="${products}" var="product">
                <div class="row">
                    <div class="col-4">
                        <c:forEach items="${product.images}" var="image">
                            <img class="img-fluid"
                                 src="${myContext}${image.imagePath}" style="padding: 5px;"
                                 alt="No image"/>
                        </c:forEach>
                    </div>
                    <div class="col-4">
                        <p>Название: ${product.name}</p>
                        <p>Описание: ${product.description}</p>
                        <p>Цена: ${product.price}</p>
                    </div>
                    <div class="col-4 align-self-center">
                        <a class="btn btn-primary btn-block" role="button"
                           href="${myContext}/basket/products/${product.id}/remove">Удалить</a>
                    </div>
                </div>
                <c:set var="totalPrice" value="${totalPrice + product.price}"/>
            </c:forEach>
            <c:if test="${not empty totalPrice}">
                <div class="center-block w-50 m-auto">
                    <p>Общая цена: ${totalPrice}</p>
                </div>
            </c:if>
            <div class="center-block w-50 m-auto">
                <sec:authorize access="isAuthenticated()">
                    <button class="btn btn-primary btn-block w-75" type="submit">Оформить заказ</button>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <a class="btn btn-primary btn-block w-75" href="${pageContext.request.contextPath}/login">Оформить заказ</a>
                </sec:authorize>
                <input type="hidden" name="totalPrice" value="${totalPrice}"/>
            </div>
        </div>
    </c:if>
</form>

<jsp:include page="footer.jsp"/>