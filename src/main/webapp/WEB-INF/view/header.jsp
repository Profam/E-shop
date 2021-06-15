<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <title>Main page</title>
    <style>
        .search-form {
            justify-content: space-between;
            align-items: center;
            margin-top: 15px;
            margin-bottom: 15px;
        }

        .form-block {
            max-width: 250px;
        }

        .form-block:first-of-type {
            display: flex;
            align-items: center;
            max-width: 300px;
        }

        .form-block:first-of-type > label {
            margin-right: 5px;
        }

        select {
            width: 200px !important;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-light bg-light d-flex">
    <div class="d-flex justify-content-start">
        <c:if test="${not pageContext.request.requestURI.endsWith('/home.jsp')}">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Главная</a>
        </c:if>
    </div>
    <div class="d-flex justify-content-end">
        <c:if test="${not empty userDetails}">
            <a class="nav-link" href="#">Привет, ${userDetails.name}</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Выйти из аккаунта</a>
        </c:if>
        <a class="nav-link" href="${pageContext.request.contextPath}/user/details?userId=${userDetails.id}">Кабинет</a>
        <a class="nav-link active" href="${pageContext.request.contextPath}/basket">Корзина</a>
    </div>
</nav>