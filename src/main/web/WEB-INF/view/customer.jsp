<%--
  Created by IntelliJ IDEA.
  User: Sc
  Date: 30.03.2019
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Customer</title>
    <style>
        <%@ include file="/WEB-INF/view/css/style.css" %>
        <%@ include file="/WEB-INF/view/css/paymentSystem.css" %>
        <%@ include file="/WEB-INF/view/css/table.css" %>
    </style>
</head>
<body>
<c:if test="${requestScope.cards.isEmpty()}">
    <p><c:out value="У вас нет не одной зарегистрированно карточки"></c:out></p>
</c:if>
<c:if test="${!requestScope.cards.isEmpty()}">
    <c:forEach var="card" items="${requestScope.cards}">
        <p class="customer"><c:out
                value="карта номер: ${card.number} баланс: ${card.balance} заблокированна: ${card.blocked}"></c:out></p>
    </c:forEach>
</c:if>
<div class="tabs">
    <input type="radio" name="inset" value="" id="tab_1" checked>
    <label for="tab_1">Платежи</label>

    <input type="radio" name="inset" value="" id="tab_2">
    <label for="tab_2">Переводы</label>

    <input type="radio" name="inset" value="" id="tab_3">
    <label for="tab_3">Заблокировать карту</label>

    <input type="radio" name="inset" value="" id="tab_4">
    <label for="tab_4">Анулировать счет</label>

    <input type="radio" name="inset" value="" id="tab_5">
    <label for="tab_5">Произведенные платежи</label>

    <div id="txt_1">
        <div class="block">
            <form id="pay_form" action="${pageContext.request.contextPath}/payment" method="post">
                <label for="card_number">Номер карты</label><input name="card_number" id="card_number" type="text"
                                                                   class="menu" placeholder="1111 2222 3333 4444"
                                                                   pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                   required><span></span><br>
                <label for="cost">Сумма</label><input name="cost" id="cost" type="number" class="menu"
                                                      required><span></span><br>
                <label for="org">Организация</label><input name="org" id="org" type="text" class="menu"
                                                           required><span></span><br>
                <div class="button">
                    <label class="qwe" for="pay_button"><input id="pay_button" form="pay_form"
                                                           type="submit">Оплатить</label>
                </div>
            </form>
        </div>
    </div>
    <div id="txt_2">
        <div class="block">
            <form id="t_form" action="${pageContext.request.contextPath}/transaction" method="post">
                <label for="t_card_number">Номер карты</label><input name="card_number" id="t_card_number" type="text"
                                                                     class="menu" placeholder="1111 2222 3333 4444"
                                                                     pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                     required><span></span><br>
                <label for="t_cost">Сумма</label><input name="cost" id="t_cost" type="number" class="menu"
                                                        required><span></span><br>
                <label for="to_card">На карту</label><input name="to_card" id="to_card" type="text" class="menu"
                                                            placeholder="1111 2222 3333 4444"
                                                            pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                            required><span></span><br>
                <div class="button">
                    <label class="qwe" for="t_button"><input id="t_button" form="t_form"
                                                             type="submit">Перевести</label>
                </div>
            </form>
        </div>
    </div>
    <div id="txt_3">
        <div class="block">
            <form id="b_form" action="${pageContext.request.contextPath}/block" method="post">
                <label for="b_card_number">Номер карты</label><input name="card_number" id="b_card_number" type="text"
                                                                     class="menu" placeholder="1111 2222 3333 4444"
                                                                     pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                     required><span></span><br>
                <div class="button">
                    <label class="qwe" for="b_button"><input id="b_button" form="b_form"
                                                             type="submit">Заблокировать</label>
                </div>
            </form>
        </div>
    </div>
    <div id="txt_4">
        <div class="block">
            <form id="d_form" action="${pageContext.request.contextPath}/delete" method="post">
                <label for="d_card_number">Номер карты</label><input name="card_number" id="d_card_number" type="text"
                                                                     class="menu" placeholder="1111 2222 3333 4444"
                                                                     pattern="\d{4}\s\d{4}\s\d{4}\s\d{4}"
                                                                     required><span></span><br>
                <div class="button">
                    <label class="qwe" for="d_button"><input id="d_button" form="d_form"
                                                             type="submit">Удалить</label>
                </div>
            </form>
        </div>
    </div>
    <div id="txt_5">
        <table>
            <tr>
                <th>Платеж</th>
                <th>Номер кредитной карты</th>
                <th>Сумма</th>
                <th>Организация</th>
                <th>На кредитную крарту</th>
                <th>Транзакиця</th>
            </tr>
            <c:forEach var="payment" items="${requestScope.payments}">
                <tr>
                    <th><c:out value="${payment.id}"></c:out></th>
                    <th><c:out value="${payment.creditCardNumber}"></c:out></th>
                    <th><c:out value="${payment.cost}"></c:out></th>
                    <th><c:out value="${payment.organization}"></c:out></th>
                    <th><c:out value="${payment.toCreditCard}"></c:out></th>
                    <th><c:out value="${payment.transaction}"></c:out></th>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
