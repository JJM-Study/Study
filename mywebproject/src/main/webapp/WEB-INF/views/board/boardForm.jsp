<!-- https://kimgom2.tistory.com/294 답변형 게시판 설계  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board</title>
<style>
   table {
    border: 1px solid;
    border-collapse : collapse;
    width:70%;
    margin-left: auto;
    margin-right: auto;
   }
   
  th {
    
    background-color: lightskyblue;

  }

  th, td {
    border: 1px solid;
    text-align: center;
    border-collapse : collapse;
   }

   caption {
     font-size: 2em;
     
   }

</style>
</head>
<body>
  <table id="board">
    <caption>Border</caption>
    <tr>
      <th>
        No.
	  	</th>
      <th>
        Title
      </th>
      <th>
        Writer
      </th>
      <th>
        Writing Date
      </th>
	  </tr>
	  
	<!-- foreach의 var, varstatus, item 등 설명 되어 있는 곳 : https://yangyag.tistory.com/302 -->
	<c:if test="${listPost == null}">
		test
	</c:if>
    <c:choose> 
	  <c:when test="${listPost != null }" >
	  	<c:forEach	var="post" items="${listPost}" varStatus="postNum">
	  	  <tr>
	  	  	<td>${postNum.count}</td>
	  	  	<td>
	  	  	  <!-- <span style="padding-right:30px"></span> <!-- 답글 -->
	  	  	  <c:choose>
	  	  	     <c:when test='${post.level > 1}'>
	  	  	     	<c:forEach begin="1" end="${post.level}" step="1">
	  	  	     		<span style="padding-left:1.250em"></span>
	  	  	     	</c:forEach>
	  	  	     	<span style="font-size:0.750em">[reply]</span>
	  	  	     	<a class='cls1' href="${contextPath}/board/viewPost?postNO=${post.postNO}">${post.title}</a>
	  	  	     </c:when>
	  	  	     <c:otherwise>
	  	  	     	<a class='cls1' href="${contextPath}/board/viewPost?postNO=${post.postNO}">${post.title}</a>
	  	  	     </c:otherwise>
	  	  	  </c:choose>
	  	  	</td>
	  	  	<td>${post.id}</td>
	  	  	<td>${post.writeDate}</td> 
	  	  </tr>
	  	</c:forEach>  
	  </c:when>
	</c:choose>
  </table>
</body>
</html>