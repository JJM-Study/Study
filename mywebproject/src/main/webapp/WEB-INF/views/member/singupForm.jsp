<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>" />

<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- https://cocoon1787.tistory.com/700 참고 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sing Up</title>
<style>

  body {width:30em; border: 1px solid black; position:relative}
  input {border: 1px solid black; border-radius: 0.2em; line-height:2.0em; font-size: 1.2em; padding-left: 0.3em; padding-right: 0.3em; width: 80%;}
  .button {cursor:pointer; height: 3em; font-size: 1em; background: rgb(30, 117, 248); border-radius:0.2em; border: 0.1em solid black; width : 7em; margin-right: 2em; color:white;}
  /* html {width: 100%; display: flex;} */
  #div_collection {width: 25em; padding-left: 17%; padding-bottom: 10%;}
  #div_btn {margin-top: 2em; display: flex;}
</style>
</head>
<body>
     <div id = "div_collection">
      <h1>Sing Up</h1>
       <form action="member/signUp" method="post" id="form">
        <div>
          <p><label for="input_name">NAME</label></p>
          <input id="input_name" type="text" name="name" placeholder="Enter Your Name">
        </div>
        <div>
          <p><label for="input_email">EMAIL</label></p>
          <input id="input_email" type="email" name="email" placeholder="Enter Your Email">
        </div>
        <div>
          <p><label for="input_id">ID</label></p>
          <input id="input_id" type="text" name="id" placeholder="Enter Your ID">
        </div>
        <div>
          <p><label for="input_pw">PASSWORD</label></p>
          <input id="input_pw" type="password" name="pwd" placeholder="Enter Your PASSWORD">
        </div>
        <div>
          <p><label for="input_chkpw">CHECK PASSWORD</label></p>
          <input id="input_chkpw" type="password" placeholder="Enter Your PASSWORD">
        </div>
        <div id="div_btn">
          <input type="button" onclick="signUp()" id="btn_signUp" class="button" value="SignUp">
          <input type="reset" value="Reset" class="button">
        </div>
       </form>
     </div>

    <script>
      var input_pw = document.getElementById("input_pw");
      var input_chkpw = document.getElementById("input_chkpw");
      var input_name = document.getElementById("input_name");
      var input_id = document.getElementById("input_id");
      var input_email = document.getElementById("input_email");
      var form_action = document.getElementById("form");
      var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

    	function signUp()
    	{
	        if (!input_name.value)
	        {
	          alert("Please Enter Your Name");
	          return;
	        }
	        if (!input_email.value)
	        {
	          alert("Please Enter Your Email");
	          return;
	        }
	        else if(!reg_email.test(input_email.value))
	        {
	            alert("Please Check Your Email");
	            return;
	        }
	        if (!input_id.value)
	        {
	          alert("Please Enter Your ID");
	          return;
	        }
	        if (!input_pw.value)
	        {
	          alert("Please Enter Your PassWord");
	          return;
	        }
	        else
	        {
	          if (!input_chkpw.value)
	          {
	            alert("Please Enter Your Check PassWord");
	            return;
	          }
	          else if(input_pw.value !== input_chkpw.value)
	          {
	            alert("Check Password Incorrect");
	            return;
	          }
	        }
	    
	        //location.href = ("${contextPath}/checkId?id=" + input_id.value);
	       
	        //location.href = ("${contextPath}/checkId?id=" + input_id.value);
	        
	     	var url = "${contextPath}/checkId";
	     	var parmas = "id=" + encodeURIComponent(input_id.value);
	        
	     	
	        var ajax = new XMLHttpRequest();
	        ajax.onreadystatechange = function() {
	        if(this.readyState == 4 && this.status == 200) {
	        	var checkId = ajax.responseText;
	        	
	        	if(checkId === "" || checkId === undefined){
	  				var form_submit = function() {
	  		  	    window.close(); // 폼 제출 후 창 닫기
	        		}; setTimeout(form_submit, 1000);
	  	        	form_action.submit();
	  		        alert("Welcome!");
	  	        } else {
	  	        	alert("ID exists");
	            }	
	          }
	        };
	     	
	        /*var ajax = new XMLHttpRequest();
	        ajax.onreadystatechange = function() {
	        if(this.readyState == 4 && this.status == 200) {
	        	//console.log(ajax.responseText);
	        	//var checkId = JSON.parse(ajax.responseText);
	        	var checkId = ajax.responseText;
	        	
	        	if(checkId === "" || checkId === undefined){
	  	        	form_action.submit();
	  		        alert("Welcome!");
	  		        //window.close();
	  	        } else {
	  	        	alert("ID exists");
	            }	
	          }
	        };  */
	        
	        ajax.open("GET", url + "?" + parmas, true);
	        ajax.send();
	        
	        
	    /*    var xhr = new XMLHttpRequest();
	        var url = "${contextPath}/checkId?id=" + input_id.value;
	        xhr.open("GET", url, true);
	        xhr.onreadystatechange = function() {
	          if (xhr.readyState == 4 && xhr.status == 200) {
	            //var checkId = "${checkID}";
	            
	            var checkId = xhr.responseText;
	  	        
	            console.log("success");
	  	        
	  	        if(checkId === "" || checkId === undefined){
	  	        	form_action.submit();
	  		        alert("Welcome!");
	  		        window.close();
	  	        }
	  	        else {
	  	        	alert("ID exists");
	  	        } 
	  	        
	            console.log(xhr.responseText);
	          }
	        };
	        xhr.send();  */

	        //const checkId = urlParams.get('checkID');
	        
	        
	        
	    /*    var xhr = new XMLHttpRequest();
	        xhr.open("GET", "${contextPath}/checkId");
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4) {
	                if (xhr.status == 200) {
	                    var checkId = xhr.responseText;
	                    console.log("success");

	                    if (checkId === "" || checkId === undefined) {
	                        form_action.submit();
	                        alert("Welcome!");
	                        window.close();
	                    } else {
	                        alert("ID exists");
	                    }
	                } else {
	                    console.error("Failed to load resource:", xhr.status);
	                }
	            }
	        };
	        var formData = new FormData();
	        formData.append("id",input_id.value);
	        xhr.send(formData); */
	        
    	}	
    	
    </script>
</body>
</html>