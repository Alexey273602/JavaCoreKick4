<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Регистрация</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Регистрация</h1>
<form action="register" method="post" class="form_class">
  <input type="text" name="username" placeholder="Логин" required>
  <input type="password" name="password" placeholder="Пароль" required>
  <button>Зарегистрироваться</button>
</form>
<a href="index.jsp"><button class="link-btn">Войти</button></a>
</body>
</html>
