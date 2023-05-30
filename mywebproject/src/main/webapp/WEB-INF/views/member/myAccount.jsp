<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보</title>
</head>
<body>
	<table>
	   <tr>
	   	 <td>
	   	 	아이디
	   	 </td>
	   	 <td>
	   	    ${memberInfo.getId()}
	   	 </td>
	   </tr>
	   <tr>
	     <td>
	        이름
	     </td>
	     <td>
	        ${memberInfo.getName()}
	     </td>
	   </tr>
	   <tr>
	     <td>
	     	이메일
	     </td>
	     <td>
	     	${memberInfo.getEmail()}
	     </td>
	   </tr>
	   <tr>
	   	 <td>
	   	    가입일
	   	 </td>
	   	 <td>
	   	   <fmt:formatDate pattern="yyyy-MM-dd" value="${memberInfo.getJoinDate()}" /></td> 
	   	 </td>
	   </tr>
	</table>
</body>
</html>