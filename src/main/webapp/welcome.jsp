<%--
  Created by IntelliJ IDEA.
  User: alexe
  Date: 11.03.2026
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Управление пиццами</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<form action="createPizza" method="post" class="form_class">
    <input type="text" name="name" placeholder="Название" required>
    <input type="number" name="size" placeholder="Размер (диаметр)" required min="20" max="50">
    <input type="number" name="price" placeholder="Цена" required min="0" step="0.01">
    <button type="submit">Добавить</button>
</form>

<table class="pizzas-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Размер</th>
        <th>Цена</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pizza" items="${pizzas}">
        <tr>
            <td>${pizza.id}</td>
            <td>${pizza.name}</td>
            <td>${pizza.size}</td>
            <td>${pizza.price}</td>
            <td>
                <form action="deletePizza" method="post" style="display: inline;">
                    <input type="hidden" name="id" value="${pizza.id}">
                    <button type="submit" class="btn-delete" onclick="return confirm('Удалить пиццу ${pizza.name}?')">
                        Удалить
                    </button>
                </form>
                <a href="editPizza?id=${pizza.id}" class="btn-edit">Изменить</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty pizzas}">
        <tr>
            <td colspan="5">Нет пицц</td>
        </tr>
    </c:if>
    </tbody>
</table>

<c:if test="${not empty editPizza}">
    <form action="updatePizza" method="post" class="form_class">
        <input type="hidden" name="id" value="${editPizza.id}">
        <input type="text" name="name" value="${editPizza.name}" placeholder="Название" required>
        <input type="number" name="size" value="${editPizza.size}" placeholder="Размер" required>
        <input type="number" name="price" value="${editPizza.price}" placeholder="Цена" required>
        <button type="submit">Обновить</button>
        <a href="?">Отмена</a>
    </form>
</c:if>
</body>
</html>
