<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
 <%-- 컨트롤러에 Post 관련 추가해서 할 것. --%>>
 <p>test : ${postView.postNO}</p>
 <p>param_test : <c:out value="${param.postNO}" /></p>
 <p>${postView}</p>
</body>
</html>