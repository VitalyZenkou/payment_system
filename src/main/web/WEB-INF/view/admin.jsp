<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Administrator</title>
    <style>
        <%@ include file="/WEB-INF/view/css/style.css" %>
        <%@ include file="/WEB-INF/view/css/paymentSystem.css" %>
        <%@ include file="/WEB-INF/view/css/table.css" %>
    </style>
</head>
<body>
<div class="tabs">
    <input type="radio" name="a_insert" value="" id="tab_1" checked>
    <label for="tab_1">Добавить карту</label>

    <input type="radio" name="a_insert" value="" id="tab_2">
    <label for="tab_2">Разблокировать/Заблокировать карту</label>

    <input type="radio" name="a_insert" value="" id="tab_3">
    <label for="tab_3">Удалить клиента</label>

    <div id="a_1">
        <div class="block">
            <form id="c_form" action="${pageContext.request.contextPath}/addCard" method="post">
                <label for="c_number">Пользователь</label><input name="number" id="c_number" type="number"
                                                                 class="menu"
                                                                 pattern="[0-9]"
                                                                 required><span></span><br>
                <label for="date">Дата действия</label><input name="date" id="date" type="text"
                                                              class="menu" placeholder="01/01"
                                                              pattern="[0-9]{2}/[0-9]{2}"
                                                              required><span></span><br>
                <div class="button">
                    <label class="qwe" for="c_button"><input id="c_button" form="c_form"
                                                             type="submit">Добавить карту</label>

                </div>
            </form>
        </div>
        <p class="card">Пользователи без добавленных кредитных карт</p>
        <table>
            <tr>
                <th>Пользователь</th>
                <th>Логин</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Администратор</th>
            </tr>
            <c:forEach var="user" items="${requestScope.users}">
                <tr>
                    <th><c:out value="${user.id}"></c:out></th>
                    <th><c:out value="${user.login}"></c:out></th>
                    <th><c:out value="${user.name}"></c:out></th>
                    <th><c:out value="${user.surname}"></c:out></th>
                    <th><c:out value="${user.administrator}"></c:out></th>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="a_2">
        <div class="block">
            <form id="cr_form" action="${pageContext.request.contextPath}/block" method="post">
                <label for="cr_number">Номер карты</label><input name="card_number" id="cr_number" type="text"
                                                                 class="menu"
                                                                 placeholder="1111 2222 3333 4444"
                                                                 pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                 required><span></span><br>
                <div class="button">
                    <label class="qwr" for="cr_button"><input id="cr_button" form="cr_form"
                                                              type="submit">Заблокировать карту</label>
                </div>
            </form>
            <form id="uc_form" action="${pageContext.request.contextPath}/unblock" method="post">
                <label for="uc_number">Номер карты</label><input name="card_number" id="uc_number" type="text"
                                                                 class="menu"
                                                                 placeholder="1111 2222 3333 4444"
                                                                 pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                 required><span></span><br>
                <div class="button">
                    <label class="qwe" for="cru_button"><input id="cru_button" form="uc_form"
                                                               type="submit">Разблокировать карту</label>
                </div>
            </form>
        </div>
        <table>
            <tr>
                <th>Карта</th>
                <th>Номер</th>
                <th>Тип</th>
                <th>Баланс</th>
                <th>Заблокированна</th>
                <th>Пользователь</th>
            </tr>
            <c:forEach var="card" items="${requestScope.cards}">
                <tr>
                    <th><c:out value="${card.id}"></c:out></th>
                    <th><c:out value="${card.number}"></c:out></th>
                    <th><c:out value="${card.creditCardType}"></c:out></th>
                    <th><c:out value="${card.balance}"></c:out></th>
                    <th><c:out value="${card.blocked}"></c:out></th>
                    <th><c:out value="${card.userId}"></c:out></th>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="a_3">
        <div class="block">
            <form id="ud_form" action="${pageContext.request.contextPath}/deleteUser" method="post">
                <label for="ud_number">Пользователь</label><input name="number" id="ud_number" type="number"
                                                                  class="menu"
                                                                  pattern="[0-9]"
                                                                  required><span></span><br>
                <div class="button">
                    <label class="qwr" for="ud_button"><input id="ud_button" form="ud_form"
                                                             type="submit">Удалить пользователя</label>

                </div>
            </form>
        </div>
        <p class="card">Пользователи системы</p>
        <table>
            <tr>
                <th>Пользователь</th>
                <th>Логин</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Администратор</th>
            </tr>
            <c:forEach var="user" items="${requestScope.usersa}">
                <tr>
                    <th><c:out value="${user.id}"></c:out></th>
                    <th><c:out value="${user.login}"></c:out></th>
                    <th><c:out value="${user.name}"></c:out></th>
                    <th><c:out value="${user.surname}"></c:out></th>
                    <th><c:out value="${user.administrator}"></c:out></th>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
