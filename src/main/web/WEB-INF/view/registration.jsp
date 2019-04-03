<%--
  Created by IntelliJ IDEA.
  User: Sc
  Date: 27.03.2019
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <style>
        <%@ include file="/WEB-INF/view/css/paymentSystem.css" %>
    </style>
</head>
<body>
<div class="block">
    <form id="form" action="${pageContext.request.contextPath}/registration" method="post">
        <label for="name">Имя</label><input name="name" id="name" type="text" class="menu" placeholder="Иван"
                                            required><span></span><br>
        <label for="surname">Фамилия</label><input name="surname" id="surname" type="text" class="menu"
                                                   placeholder="Иванов"
                                                   required><span></span><br>
        <label for="city">Город</label><input name="city" id="city" type="text" class="menu"
                                              placeholder="Минск"
                                              required><span></span><br>
        <label for="street">Улица</label><input name="street" id="street" type="text" class="menu"
                                                placeholder="пр. Пушкина"
                                                required><span></span><br>
        <label for="house">Дом</label><input name="house" id="house" type="number" class="menu"
                                             placeholder="1"
                                             pattern="[0-9]{1,}"
                                             required><span></span><br>
        <label for="flat">Квартира</label><input name="flat" id="flat" type="number" class="menu"
                                                 placeholder="2"
                                                 pattern="[0-9]{1,}"
                                                 required><span></span><br>
        <label for="phone">Телефон</label><input name="phone" id="phone" type="tel" class="menu"
                                                 placeholder="(29)111-22-33"
                                                 required
                                                 pattern="\(\d{2}\)\d{3}-\d{2}-\d{2}">
        <span></span><br>
        <label for="login">Логин</label><input name="login" id="login" type="text" class="menu"
                                               placeholder="Инван-Иванов"
                                               required><span></span><br>
        <label for="password">Пароль</label><input name="password" id="password" type="password" class="menu"
                                                   pattern="[0-9]{7,}"
                                                   required placeholder="Введите пароль"><span></span><br>
        <label for="password">Повторите</label><input ud="password" type="password" class="menu"
                                                      placeholder="Повторите пароль" required
                                                      pattern="[0-9]{7,}"><span></span><br>
        <div class="button">
            <label class="qwe" for="button"><input id="button" form="form" type="submit">Зарегистрироваться</label>
        </div>
    </form>
</div>
</body>
</html>
