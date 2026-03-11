<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Авторизация</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Авторизация</h1>
<form action="login" method="post" class="form_class">
    <input type="text" name="username" placeholder="Логин" required>
    <input type="password" name="password" placeholder="Пароль" required>
    <button>Войти</button>
</form>
<a href="register.jsp"><button class="link-btn">Регистрация</button></a>
</body>
</html>
