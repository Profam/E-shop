<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <title>Register</title>
    <style>
        .w-25 {
            margin: 0 auto;
        }
    </style>
</head>
<body>

<div class="container" style="text-align: center;">
    <form:form class="form-signuup" method="POST" action="${pageContext.request.contextPath}/register/new" modelAttribute="userDetails">
        <h2 class="form-signup-heading">Регистрация</h2>
        <p>
            <form:label path="name">Имя</form:label>
            <form:input type="text" path="name" name="name" class="form-control w-25" placeholder="Имя"/>
        </p>
        <p>
            <form:label path="surname">Фамилия</form:label>
            <form:input type="text" path="surname" name="surname" class="form-control w-25" placeholder="Фамилия"/>
        </p>
        <p>
            <form:label path="email">Email</form:label>
            <form:input type="email" path="email" name="email" class="form-control w-25" placeholder="Email"/>
        </p>
        <p>
            <input type="email" id="confirmEmail" name="confirmEmail" class="form-control w-25" placeholder="Подтвердите Email"
                   onblur="if(this.value!=document.getElementById('email').value) alert('Email адреса не совпадают!')" aria-required="true" required autofocus>
        </p>
        <p>
            <form:label path="password">Пароль</form:label>
            <form:input type="password" path="password" name="password" class="form-control w-25" placeholder="Пароль"/>
        </p>
        <p>
            <form:label path="birthday">День рождения</form:label>
            <form:input type="date" path="birthday" name="birthday" min="1921-01-01" class="form-control w-25" placeholder="MM/dd/yyyy"/>
        </p>
        <form:input type="hidden" path="balance" value="0"/>
        <button class="btn btn-lg btn-primary btn-block w-25" type="submit" value="registerNewUser">Зарегистироваться</button>
    </form:form>
</div>

<jsp:include page="footer.jsp"/>