<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>로그인</title>
	<link rel="stylesheet" href="css/login.css" type="text/css">
	</head>

	<body>	
	
		<div id="wrap">
            <div id= "login">
				<form action="Login" method="post" name = "frm">
	                <fieldset>
	                    <div id="id">
	                        <span>아이디 </span>
	                        <input type="text" name="u_id">
	                    </div>
	                    <div id="pw">
	                        <span>비밀번호 </span>
	                        <input type="password" name="u_pw">
	                    </div>
	                    <div id="log">
	                        <input type="submit" value="로그인" onclick="return check()">
	                        <input type="button" value="회원가입" onclick="join()">
	                    </div>
	                    <div>${message}</div>
	                </fieldset>
				</form>
            </div>
        </div>
	</body>
	<script>
		
		function join(){
			location.href = "Join";
		}
		
		function check(){
			if(document.frm.u_id.value.length==0){
				alert("아이디를 입력하세요");
				frm.u_id.focus();
				return false;
			}
			if(document.frm.u_pw.value==""){
				alert("비밀번호를 입력하세요");
				frm.u_pw.focus();
				return false;
			}
			return true;
		}
		
	</script>
</html>