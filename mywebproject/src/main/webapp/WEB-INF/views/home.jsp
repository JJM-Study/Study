<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ page session="false" --%>
<%-- http://javakorean.com/jsp-%ED%8C%8C%EC%9D%BC%EC%97%90%EC%84%9C-contextpath-%EB%B6%88%EB%9F%AC%EC%98%A4%EA%B8%B0/ --%>
<!-- 탭 기능 구현 : https://bbo-blog.tistory.com/96 --> 
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Home Site
</h1>
	
	<!-- 로그인 사용자 간단 정보 출력 만드는 중 ... -->
	<c:if test="${memberInfo ne null}">
		<div>
		   <p>로그인 : <c:out value="${memberInfo.getId()}" /></p>
		</div>
	</c:if>
	
	<!-- ------------ -->
	
	<ul>
	  <c:choose>
	  	<c:when test="${isLogOn==true && not empty memberInfo}">
	  	  <!-- @RequestMapping에 관하여 href 적용 원리 https://okky.kr/questions/284703 -->
	  	  <li><a href="${contextPath}/logout">로그아웃</a></li>
	  	  <li><a href="${contextPath}/myAccount">내정보</a></li>
	  	</c:when>
	  	<c:otherwise>
	  	  <li><a href="${contextPath}/loginForm">로그인</a></li>
	  	  <li>회원정보</li>
		</c:otherwise>
	  </c:choose>
	</ul>
<!-- <a href="${contextPath}/board/boardForm">(임시)게시판 바로가기</a> -->
<!-- <a href="Listboard">(임시)게시판 바로가기</a>  -->
	<a href="Listboard?page=1">(임시)게시판 바로가기</a>
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
