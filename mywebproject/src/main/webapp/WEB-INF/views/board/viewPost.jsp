<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />
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

  </style>
<meta charset="UTF-8">
<title></title>

</head>
<body>
 <div class="t_container">
	<table class="t_post">
		<caption class="t_post_cap">Board View</caption>
		<tr class="t_post">
			<th class="f_col">
				Title
			</th>
			<td class="t_post_title" colspan="3">
				<input type="text" value=${postView.title} id="input_title" readonly>
			</td>
		</tr>
		<tr>
			<th class="t_post">
				Writer
			</th>
			<td class="t_post s_col">
				${postView.id}
			</td>
			<th class="t_post t_col">
				Wrting Date
			</th>
			<td class="t_post four_col">
				${postView.writeDate}
			</td>
		</tr>
		<tr class="t_post">
			<th class="t_post t_row">
				Contents
			</th>
			<td>
				<textarea id="input_cnt" readonly>${postView.content}</textarea>
			</td>
		</tr>
		<tr class="t_post">
			<td colspan="4">
				<input type="submit" value="삭제" id="btn_cancel" class="btn_post">
				<input type="submit" value="수정" id="btn_edit" onclick="login_chk()" class="btn_post">
			</td>
		</tr>
	</table>
</div>
</div>
</body>
<script>

	 function login_chk() {
			var isLogOn = "${isLogOn}"
			var id = "${memberInfo.id}"
			if (isLogOn)
			{
				if (id === "${postView.id}") 
				{
					edit_chk();
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
	 
	 function edit_chk() {
			var btn_edit = document.getElementById("btn_edit");
			var btn_cancel = document.getElementById("btn_cancel");
			var input_title = document.getElementById("input_title"); // 게시글 제목
			var	input_cnt = document.getElementById("input_cnt"); // 게시글 내용

			if (btn_edit.value == "수정"){
		  	btn_edit.value = "수정 적용";
				btn_cancel.style.display='none';
				input_title.readOnly = false;
				input_cnt.readOnly = false; // 게시글 내용
			}
			else {
				btn_edit.value = "수정";
				btn_cancel.style.display='block';
				input_title.readOnly = true;
				input_cnt.readOnly = true;
			}
	 }
	 
</script>
<footer>
<p>test : ${postView.postNO}</p>
<p>param_test : <c:out value="${param.postNO}" /></p>
</footer>
</html>

