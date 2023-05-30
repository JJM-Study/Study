<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />

<%
	request.setCharacterEncoding("UTF-8");
%>
    					
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<body>
	<h1 bgcolor ="40cce2">회원 리스트</h1>
	<table >
	   <tr>
		 <td>${memberList}</td>
		 <td></td>
		 <td></td>
	   </tr>
	</table>
</body>
</html>