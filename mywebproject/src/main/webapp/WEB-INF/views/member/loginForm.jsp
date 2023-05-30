<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<c:if test='${not empty message}'>
 <script>
   // https://velog.io/@leyuri/javaScript-window.onload%EB%9E%80 window.onload 개념
   window.onload = function(){
	   result()
   }
   
   function result() {
	   alert("<c:out value="${message}" />");
   }
 </script>
</c:if>
</head>
<body>
<!--	<ul>
		<li>아이디 : <input type="text" name="id"></li>
	</ul> -->
  <form action="login" method="POST">
	<table>
	  <tr>
		<td>
		  <label for="userId">아이디</label>
		</td>
		<td>
		  <input type="text" id="userId" name="id">
		</td>
	  </tr>
	    <td>
	      <label for="pw">비밀번호</label>
	    </td>
	    <td>
	      <input type="text" id="pw" name="pwd">
	    </td>
	</table>
	
	<input type="submit" value="로그인">
  </form>
  
</body>
</html>