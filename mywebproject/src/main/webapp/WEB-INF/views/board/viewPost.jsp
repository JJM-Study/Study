<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %> 
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
<jsp:useBean id="javaDate" class="java.util.Date" />
<fmt:formatDate var="nowDate" value="${javaDate}" pattern="yyyy-MM-dd"/>
<!DOCTYPE html>
<html>
<head>
	<!-- https://tastyloper.tistory.com/1 - 테이블 내 textarea 넣기-->
  <style>
		th {background: rgb(173, 230, 252);}
    .t_post {border: 1px gray solid ; border-collapse: collapse; padding: 5px; margin-top: 120px; margin: auto;}
		.t_post_title {border: 1px gray solid ; border-collapse: collapse;}
		
		.f_col {width:300px;}
		.s_col {width:300px;}
		.t_col {width:200px;}
		.four_col  {width:250px;}
		.t_row {height: 500px;}
		.t_post_cap {font: 35px bold; margin-bottom: 10px;}
		.btn_post {float:right; margin-left: 5px; padding: 5px 10px; border: 1px solid; background-color:skyblue;}
		
		#input_title {border:0; font-size:16px; padding : 5px; width:98%; outline:none;}
		#input_cnt {width:97%; height:497px; padding:5px; border: 0; outline:none; resize:none; font-size: 17px;}
		#input_writeDate {resize: none; font-size: 16px; border:0; outline:none;}
		#input_id {resize: none; font-size: 16px; border:0; outline:none;}
		
		#btn_edit_comple {display: none;}
  </style>
<meta charset="UTF-8">
<title></title>

</head>
<body>
<!-- <form name="update" action="${contextPath}/board/updatePost?postNO=${postView.postNO}" id="form"> -->
<form name="update" action="${contextPath}" id="form">
 <div class="t_container">
	<table class="t_post">
		<caption class="t_post_cap">Board View</caption>
		<tr class="t_post">
			<th class="f_col">
				Title
			</th>
			<td class="t_post_title" colspan="3">
				<input type="text" value="${postView.title}" id="input_title" name="title" autofocus tabindex="1" readonly>
			</td>
		</tr>
		<tr>
			<th class="t_post">
				Writer
			</th>
			<td class="t_post s_col">
				<input type="text" value="${postView.id}" id="input_id" name="id" readonly>
			</td>
			<th class="t_post t_col">
				Wrting Date
			</th>
			<td class="t_post four_col">
				<input type="text" value="${postView.writeDate}" id="input_writeDate" name="writeDate" readonly>
			</td>
		</tr>
		<tr class="t_post">
			<th class="t_post t_row">
				Contents
			</th>
			<td>
				<textarea id="input_cnt" name="content" tabindex="2" readonly>${postView.content}</textarea>
			</td>
		</tr>
		<tr class="t_post">
			<td colspan="4">
				<input type="button" value="삭제" id="btn_cancel" onclick="login_chk(this.id)" class="btn_post">
				<input type="button" value="수정" id="btn_edit" onclick="login_chk(this.id)" class="btn_post">
				<input type="submit" value="수정 완료" id="btn_edit_comple" class="btn_post">
				<input type="button" value="글쓰기" id="btn_post" onclick="login_chk(this.id)" class="btn_post">
				<input type="button" value="답글" id="btn_reply" onclick="login_chk(this.id)" class="btn_post"">	<!-- 2023/06/26 추가 -->
				<input type="hidden" name="imageFileName" value="null">
				<input type="hidden" name="postNO" id="input_postNO" value=${postView.postNO}>
				<!-- <input type="hidden" name="id" value="${memberInfo.id}"> -->
				<input type="hidden" name="level" value=0> <!-- 레벨 0은 글쓰기. 1은 댓글 -->
				<input type="hidden" name="parentNO" id="input_parentNO" value=0>
				<input type="hidden" name="arr" id="input_arr" value=0> <!-- 2022/06/26 추가-->
			</td>
		</tr>
	</table>
 </div>
</form>
</body>
<script>

	var contextPath = window.location.origin;
	var btn_post = document.getElementById("btn_post");
	var btn_cancel = document.getElementById("btn_cancel");
	var btn_edit = document.getElementById("btn_edit");
	var btn_reply = document.getElementById("btn_reply"); // 2023/06/26 조재만 추가
	var isPosted = "${postView.postNO}"; // ★★★★★ 2023/06/07 작성 타이틀의 null 유무 / 경우에 따라 후에 조건을 Insert 기준으로 맞춰서 수정할 것.
	var input_title = document.getElementById("input_title"); // 게시글 제목
	var	input_cnt = document.getElementById("input_cnt"); // 게시글 내용
	var postNO = document.getElementById("input_postNO");
	var input_arr = document.getElementById("input_arr");
	var input_parentNO = document.getElementById("input_parentNO");
	var input_id = document.getElementById("input_id");
	var input_writeDate = document.getElementById("input_writeDate");
	
	if (!isPosted)
	{
		input_id.value = "${memberInfo.id}";
		btn_edit.style.display = 'none';
		btn_cancel.style.display = 'none';
		btn_reply.style.display = 'none';
		input_title.readOnly = false;
		input_cnt.readOnly = false; // 게시글 내용
		postNO.value=1;
	}
	else
	{
		btn_post.style.display = 'none';
	}


	function login_chk(e) {
		var isLogOn = "${isLogOn}"
		var id = "${memberInfo.id}"

		if (id)
		{
			if (id === "${postView.id}")
			{
				if (e == "btn_edit")
				{
					edit_chk();
				}
				else if (e == "btn_cancel")
				{
					del();
				}
				else if (e == "btn_reply") // 2023/06/26 조재만 추가
				{
					reply();
				}
			}
			else if("${memberInfo.id}")
			{
				posting();
			}
			else 
			{
				alert("작성자만 수정할 수 있습니다.");
			}
		}
		else
		{
			alert("로그인이 필요합니다.");
		}
	}
	
	function reply() { // 2023/06/26 조재만 추가
		//if (btn_edit.style.display !== 'none')
		if (btn_reply.value == "답글")
		{
			btn_edit.style.display = 'none';
			btn_cancel.style.display = 'none';
			alert(document.getElementById("input_parentNO").value = "${postView.postNO}");
			btn_reply.setAttribute("value", "글쓰기");
			input_title.value = null;
			input_cnt.value = null;
			input_id.value = "${memberInfo.id}"
			input_writeDate.value = null;
			input_title.readOnly = false;
			input_cnt.readOnly = false;
			input_title.focus();
			input_arr.value = "${postView.arr}"
			input_parentNO.value = "${postView.postNO}"
		}
		else
		{
			input_writeDate.value = "${nowDate}"
			form.action = "${contextPath}/board/replyPost";
			document.getElementById("form").submit();
		}
	
	}
	
	function del() {
	  //location.href = "${contextPath}/board/deletePost?postNO=" + isPosted;
		form.action = "${contextPath}/board/deletePost";
		document.getElementById("form").submit();
	}
	 
	function edit_chk() {
		var btn_edit_comple = document.getElementById("btn_edit_comple");
		var form = document.getElementById("form");

			//if (btn_edit.display == "block"){
		  	//btn_edit.value = "수정 적용";
		btn_cancel.style.display='none';
		btn_edit.style.display='none';
		input_title.readOnly = false;
		input_cnt.readOnly = false; // 게시글 내용
		btn_edit_comple.style.display='block';
		form.action = "${contextPath}/board/updatePost";
	}
			
	function posting() {
		//location.href= contextPath + "myproject/board/insertPost;
		input_writeDate.value = "${nowDate}"
		form.action = "${contextPath}/board/insertPost";
		document.getElementById("form").submit();
	}

			// else {
			// 	btn_edit.value = "수정";
			// 	btn_cancel.style.display='block';
			// 	input_title.readOnly = true;
			// 	input_cnt.readOnly = true;
			// 	location.href= contextPath + "myproject/board/updatePost?postNO=${postView.postNO}";
	 
</script>
<footer>
<p>test : ${postView.postNO}</p>
<p>param_test : <c:out value="${param.postNO}" /></p>
<p>arr : ${postView.arr}</p>
<p>parentNO : ${postView.postNO}</p>
</footer>
</html>