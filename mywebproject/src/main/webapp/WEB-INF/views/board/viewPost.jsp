<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${listPost}</title>
</head>
<body>
 <p>test : ${listPost.postNO}</p>
 <p>param_test : <c:out value="${param.postNO}" /></p>
</body>
</html>