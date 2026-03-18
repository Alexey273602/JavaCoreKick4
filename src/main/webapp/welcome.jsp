<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Управление пиццами</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 1200px; margin: 0 auto; padding: 20px; }
        .pizzas-table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .btn-delete { background: #f44336; color: white; border: none; padding: 8px 12px; cursor: pointer; border-radius: 4px; }
        .btn-delete:hover { background: #d32f2f; }
        .btn-edit { background: #2196F3; color: white; padding: 8px 12px; text-decoration: none; border-radius: 4px; margin-left: 5px; }
        .btn-edit:hover { background: #1976D2; }
        .form_class { margin: 20px 0; padding: 20px; border: 2px solid #4CAF50; border-radius: 8px; background: #f9f9f9; }
        .form_class input { margin: 5px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .form_class button { background: #4CAF50; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
        h1 { color: #4CAF50; text-align: center; }
    </style>
</head>
<body>
<h1>🍕 Управление пиццами</h1>

<form action="${pageContext.request.contextPath}/pizzas" method="post" class="form_class">
    <h3>➕ Добавить новую пиццу</h3>
    <input type="hidden" name="action" value="create">
    <input type="text" name="name" placeholder="Название пиццы" required>
    <input type="number" name="size" placeholder="Размер (см)" required min="20" max="50">
    <input type="number" name="price" placeholder="Цена (₽)" required min="0">
    <button type="submit">Добавить пиццу</button>
</form>

<table class="pizzas-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Размер (см)</th>
        <th>Цена (₽)</th>
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
                <form action="${pageContext.request.contextPath}/pizzas" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${pizza.id}">
                    <button type="submit" class="btn-delete"
                            onclick="return confirm('Удалить пиццу ${pizza.name}?')">
                        🗑️ Удалить
                    </button>
                </form>
                <a href="${pageContext.request.contextPath}/pizzas/edit/${pizza.id}" class="btn-edit">
                    ✏️ Изменить
                </a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty pizzas}">
        <tr>
            <td colspan="5" style="text-align:center; color:#666; padding:40px;">
                Нет пицц в меню. Добавьте первую! 🍕
            </td>
        </tr>
    </c:if>
    </tbody>
</table>

<c:if test="${not empty editPizza}">
    <div class="form_class" style="background: #fff3cd;">
        <h3>✏️ Редактирование пиццы: ${editPizza.name}</h3>
        <form action="${pageContext.request.contextPath}/pizzas" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${editPizza.id}">
            <input type="text" name="name" value="${editPizza.name}" placeholder="Название" required>
            <input type="number" name="size" value="${editPizza.size}" placeholder="Размер" required min="20" max="50">
            <input type="number" name="price" value="${editPizza.price}" placeholder="Цена" required min="0">
            <button type="submit" style="background:#2196F3;">💾 Сохранить изменения</button>
            <a href="${pageContext.request.contextPath}/pizzas" style="margin-left:10px; color:#666;">❌ Отмена</a>
        </form>
    </div>
</c:if>

<div style="text-align:center; margin-top:30px; color:#666;">
    <a href="${pageContext.request.contextPath}/">← Главная</a>
</div>
</body>
</html>
