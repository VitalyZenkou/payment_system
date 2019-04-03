<%--
  Created by IntelliJ IDEA.
  User: Sc
  Date: 29.03.2019
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Sc
  Date: 25.03.2019
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <style>
        <%@ include file="/WEB-INF/view/css/paymentSystem.css" %>
    </style>
</head>
<body>
<div class="block">
    <form id="lForm" action="${pageContext.request.contextPath}/login" method="post">
        <label for="login">Логин</label><input name="login" id="login" type="name" class="menu"
                                               required><span></span><br>
        <label for="password">Пароль</label><input name="password" id="password" type="password" class="menu"
                                                   required><span></span><br>
        <div class="button">
            <label class="qwe" for="button"><input id="button" form="lForm" type="submit">Войти</label>
        </div>
    </form>
</div>
<div>
    <form id="rForm" action="${pageContext.request.contextPath}/registration" method="get">
        <div class="rbutton">
            <label class="qwr" for="rbutton"><input id="rbutton" form="rForm" type="submit">Регистрация</label>
        </div>
    </form>
</div>
</body>
</html>

