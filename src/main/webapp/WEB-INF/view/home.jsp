<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>

<div class="container-fluid">
    <form class="d-flex" action="${myContext}/search" method="get">
        <input class="form-control me-2" type="search" name="search" placeholder="Поиск" aria-label="Поиск">
        <button class="btn btn btn-outline" type="submit">Поиск</button>
    </form>
</div>

<div class="d-flex">
    <a class="nav-link" href="#">Каталог</a>
</div>

<c:if test="${not empty categories}">
    <div class="row" type="categoriesLinks">
        <c:forEach items="${categories}" var="category">
            <div class="card w-25 m-1" type="category">
                <a href="${myContext}/categories/${category.id}?currentPage=1&recordsPerPage=10">
                    <img class="card-img" src="${myContext}${category.imagePath}" alt="No image"/>
                    <div class="card-img-overlay">
                        <h5 class="card-title bg-dark text-red w-50">${category.name}</h5>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</c:if>

<jsp:include page="footer.jsp"/>