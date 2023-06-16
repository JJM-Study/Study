<!-- 페이징 참고 : https://pingfanzhilu.tistory.com/entry/JSP-Practice-3-application%EC%98%81%EC%97%AD%EC%9C%BC%EB%A1%9C-CRUD-%ED%8E%98%EC%9D%B4%EC%A7%95Paging -->

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
	  	  	<!-- <td>${postNum.count}</td>  -->
	  	  	<td>${post.postNO}</td>
	  	  	<td>
	  	  	  <!-- <span style="padding-right:30px"></span> <!-- 답글 -->
	  	  	  <c:choose>
	  	  	     <c:when test="${post.level < 1}">
					<a class='cls1' href="${contextPath}/board/viewPost?postNO=${post.postNO}">${post.title}</a>
	  	  	     </c:when>
	  	  	     <c:otherwise>
	  	  	     	<c:forEach begin="1" end="${post.level}" step="1">
	  	  	     	<!-- <span style="padding-left:1.250em"></span>  -->
	  	  	     	</c:forEach>
	  	  	     	<a class='cls1' href="${contextPath}/board/viewPost?postNO=${post.postNO}">${post.tree_title}</a>
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
  <input type="button" value="글쓰기" onclick="location.href='${contextPath}/board/posting'">
 
  <!-- 2023/06/16 여기서부터 수정 부분-->

  <div class="page_container">
    <ul id="page_n">
        <li>
          <a href="">1</a>
        </li>
  </ul>
  </div>
  <p>${b_count}</p>
</body>
  <script>
  //var pageCount = document.getElementsByClassName("page_n").length;
  
  var postCount = document.getElementById("board").rows.length - 1;
  var totalPage = "${b_count}";
  var lmtPage = 10; // 페이지 상한
  var li ="";

  document.writeln(postCount);
  // https://webisfree.com/2017-03-30/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%EC%9D%98-innerhtml%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%EC%97%98%EB%A6%AC%EB%A8%BC%ED%8A%B8-%EC%B6%94%EA%B0%80%EC%8B%9C-%EC%84%B1%EB%8A%A5%ED%96%A5%EC%83%81%EC%9D%84-%EC%9C%84%ED%95%B4-%EC%83%9D%EA%B0%81%ED%95%A0-%EB%B6%80%EB%B6%84
  // 위는 자바스크립트 li 및 a href 추가
  for(i = 1; i <= Math.ceil((totalPage / lmtPage)); i++) //여기서 10은 페이지숫자 변수로 대체 // 토탈페이지도 나눠서 0이 아닐 경우 페이지 추가하도록 수정할 것.
  {
     li = li + '<li><a href="Listboard?page=' + i + '">' + i + '</li>';
  }
  document.getElementById('page_n').innerHTML = li;
  
  var pageCount = document.getElementById("page_n").childElementCount;
  document.writeln(pageCount);

</script>
</html>