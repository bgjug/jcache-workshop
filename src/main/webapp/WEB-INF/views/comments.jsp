<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Bulgarian JUG</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body id="comments">
	<div>
		<div class="logged-user">
			Hello, <c:out value="${user.firstName}"/>
		</div>
		<form action="comment/search" method="post">
			<input type="text"placeholder="Search" name="searchTerm">
			<button id="search-btn" class="btn" type="submit">Go!</button>
		</form>
		<div class="app-title">
		    <h1>Bulgarian JUG guestbook</h1>
		</div>
	    <table>
	        <tr>
	            <th>Title</th>
	            <th>Content</th>
	            <th>Author</th>
	            <c:if test="${user.adminUser}"><th>Admin</th></c:if>
	        </tr>
	
	        <c:forEach items="${comments}" var="comment" varStatus="loop">
	            <tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
	                <td><c:out value="${comment.title}"/></td>
	                <td><c:out value="${comment.content}"/></td>
	                <td><a href="user?userName=${comment.byUser.userName}">
						<c:out value="${comment.byUser.firstName}"/>
	                    <c:out value="${comment.byUser.lastName}"/></a></td>
	                <c:if test="${user.adminUser}"><td><a href="comment/delete?commentId=${comment.id}">Delete</a></td></c:if>
	            </tr>
	        </c:forEach>
	    </table>
	    <br />
		<a href="newcomment">Add comment</a>
		<br /><br /><br /><span>Expiry policy is : ${statistics.getExpiryPolicyFactory() != null ? 'set' : 'not set'}</span>
	</div>
</body>
</html>
