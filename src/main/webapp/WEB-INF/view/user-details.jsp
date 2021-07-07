<jsp:include page="header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<div class="container-fluid">
    <c:if test="${not empty userDetails}">
        <div class="mx-auto" style="width: 200px;">
            <h5>
                Личные данные
            </h5>
        </div>
        <div class="row">
            <div class="col-3">
                <div class="p-2">"Имя: ${userDetails.name}"</div>
                <br/>
                <div class="p-2">"Фамилия: ${userDetails.surname}"</div>
                <br/>
            </div>
            <div class="col-3">
                <div class="p-2">"День рождения: ${userDetails.birthday}"</div>
                <br/>
                <div class="p-2">"Email: ${userDetails.email}"</div>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty orders}">
        <div class="mx-auto" style="width: 200px;">
            <h5>
                История заказов
            </h5>
        </div>

        <div class="container-fluid">
            <c:forEach items="${orders}" var="order">
                <div class="container-fluid">
                    <p>Заказ: ${order.id} / Дата заказа: ${order.date}</p>
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <c:forEach items="${order.products}" var="product">
                            <div class="col-2">
                                <c:forEach items="${product.images}" var="image">
                                    <img class="w-100"
                                         src="${myContext}${image.imagePath}" style="padding: 5px;"
                                         alt="No image"/>
                                </c:forEach>
                            </div>
                            <div class="col-2">
                                <p>Название: ${product.name}</p>
                                <p>Описание: ${product.description}</p>
                                <p>Цена: ${product.price}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>