<!doctype html>
<html lang="en">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <title>Login</title>
    <style>
        .w-25 {
            margin: 0 auto;
        }
    </style>
</head>
<body>

<div class="container" style="text-align: center;">
    <form class="needs-validation" method="post" action="${pageContext.request.contextPath}/login" novalidate>
        <h2 class="needs-validation-heading">Личный кабинет</h2>
        <p>
            <input type="email" id="email" name="email" class="form-control w-25" placeholder="Логин" required autofocus>
        </p>
        <p>
            <input type="password" id="password" name="password" class="form-control w-25" placeholder="Пароль" required>
        </p>
        <p>
            <button class="btn btn-primary btn-block w-25" type="submit">Вход</button>
        </p>
    </form>
    <p>
        <a class="btn btn-primary w-25" href="${pageContext.request.contextPath}/register" role="button">Регистрация</a>
    </p>
</div>

<script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<jsp:include page="footer.jsp"/>