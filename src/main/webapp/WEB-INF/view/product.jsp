<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="header.jsp"/>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<form action="${myContext}/basket/products/${product.id}/add" method="post">
    <c:if test="${not empty product}">
        <div type="product">
            <div class="row">
                <div class="col-6">
                    <c:forEach items="${product.images}" var="image">
                        <div class="row">
                            <img class="w-100" src="${myContext}${image.imagePath}"
                                 alt="No image"/>
                        </div>

                        <div class="row">
                            <div class="gallery_product col-lg-3">
                                <img src="${myContext}${image.imagePath}" alt="No image"
                                     class="w-100">
                            </div>

                            <div class="gallery_product col-lg-3">
                                <img src="${myContext}${image.imagePath}" alt="No image"
                                     class="w-100">
                            </div>


                            <div class="gallery_product col-lg-3">
                                <img src="${myContext}${image.imagePath}" alt="No image"
                                     class="w-100">
                            </div>


                            <div class="gallery_product col-lg-3">
                                <img src="${myContext}${image.imagePath}" alt="No image"
                                     class="w-100">
                            </div>

                        </div>
                    </c:forEach>
                </div>
                <div class="col-6">
                    <div class="row">
                        <div class="col-6">
                            <p>Название товара: ${product.name}</p>

                            <p>Описание: ${product.description}</p>

                            <p>Цена: ${product.price}</p>
                        </div>
                        <p>
                            <button class="btn btn-primary btn-block w-75" type="submit">Купить!</button>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</form>

<jsp:include page="footer.jsp"/>