<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Bulgarian JUG</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>

<body>
<h1>User profile of <c:out value="${user.userName}"></c:out></h1>
<p>
<b>First name:</b> <c:out value="${user.firstName}"></c:out><br>
<b>Last name:</b> <c:out value="${user.lastName}"></c:out><br>
<b>Admin?:</b> <c:out value="${user.adminUser}"></c:out><br>
</p>
</body>