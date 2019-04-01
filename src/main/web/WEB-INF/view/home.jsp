<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<form id="form" action="${pageContext.request.contextPath}/exit" method="get">
    <div class="button">
        <label class="qwr" for="button"><input id="button" form="form" type="submit">Выйти</label>
    </div>
</form>
<h1><c:out value="Ну здрасте ${requestScope.user.name}"></c:out></h1>
<c:if test="${requestScope.user.isAdministrator()}">
    <%@ include file="admin.jsp" %>
</c:if>
<c:if test="${!requestScope.user.isAdministrator()}">
    <%@ include file="customer.jsp" %>
</c:if>
</body>
</html>
