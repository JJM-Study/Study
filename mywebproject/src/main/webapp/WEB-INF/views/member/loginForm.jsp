<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
	 #logdiv {text-align: center; background-color: skyblue; width: 30em; height: 10em; margin:auto; padding-top: 0.1em; margin-top: 10em;}
	 #userId {font-size: 1.2em;}
	 #pw {font-size: 1.2em;}
	 #login_table {font-size: 1.2em; font-weight: bold;}
	 .btn {margin-top: 0.5em; font-size: 1.5em; background:rgb(30, 117, 248); color: white; width:5em;}
	 #logbtn_div {}
	 #btn_login {}
	 #lbl_id {padding-right: 1em;}
</style>
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
<div id="logdiv">
<!--<table id = "login_table">
	  <tr class="row">
		<td>
		  <label for="userId" class="login">아이디</label>
		</td>
		<td>
		  <input type="text" id="userId" class="login" name="id">
		</td>
	  </tr>
	    <td>
	      <label for="pw" class="loing">비밀번호</label>
	    </td>
	    <td>
	      <input type="password" id="pw" class="login" name="pwd">
	    </td>
	</table> -->
		<p><label for="userId" class="login" id="lbl_id">아이디</label>
		<input type="text" id="userId" class="login" name="id"></p>
		<p><label for="pw" class="loing">비밀번호</label>
		<input type="password" id="pw" class="login" name="pwd"></p>
		<div id="logbtn_div">
			<input type="submit" value="로그인" id="btn_login" class="btn">
			<input type="button" value="회원가입" id="btn_signup" class="btn" onclick="window.open('${contextPath}/signUp', 'Sing_Up', 'width=500, height=700, location=no, scrollbars	=no');">
		</div>
</div>
</form>
  
</body>
</html>