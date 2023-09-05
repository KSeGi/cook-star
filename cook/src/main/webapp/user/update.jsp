<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원정보수정 페이지</title>
		<link rel="stylesheet" href="css/update.css">

	</head>
	<body>
		<div id="wrap">
			<h1>정보 수정</h1>
			<form action = "Update" method="post">
			<c:if test = "${sessionScope.user.u_grade==1}">
				<input type="hidden" name = "u_no" value="${user.u_no}" readonly>
					<input type="hidden" name = "u_grade" value="${user.u_grade}" required>
					<span>ID</span><input type="text" name = "u_id" class="read" value="${user.u_id}" readonly >
					<span>PW</span><input type="text" name = "u_pw"  required >
					<span>PW 확인</span><input type="text" name = "u_pw" required >
					<span>이름</span><input type="text" name = "u_name" value="${user.u_name}" required >
					<span>닉네임</span><input type="text" name = "u_nik" value="${user.u_nik}" required >
					<span>연락처</span><input type="text" name = "u_tel" value="${user.u_tel}" required >
					<span>가입날짜</span><input type="text" name = "u_joindate" class="read" value="${user.u_joindate}" readonly>
					<input type="submit" value="수정하기" class="btn"> 
					<input type="reset" value ="다시쓰기" class="btn">
			</c:if>
			<c:if test = "${sessionScope.user.u_grade==2}">
					<span>회원 번호:</span><input type="text" name = "u_no" class="read" value="${user.u_no}"readonly>
					<span>등급:</span><input type="text" name = "u_grade" value="${user.u_grade}" required>
					<span>ID</span><input type="text" name = "u_id" class="read" value="${user.u_id}" readonly >
					<span>PW</span><input type="text" name = "u_pw" value="${user.u_pw}" required >
					<span>PW 확인</span><input type="text" name = "u_pw" value="${user.u_pw}" required >
					<span>이름</span><input type="text" name = "u_name" value="${user.u_name}" required >
					<span>닉네임</span><input type="text" name = "u_nik" value="${user.u_nik}" required >
					<span>연락처</span><input type="text" name = "u_tel" value="${user.u_tel}" required >
					<span>가입날짜</span><input type="text" name = "u_joindate" class="read" value="${user.u_joindate}" readonly>
					<input type="submit" value="수정하기" class="btn"> 
					<input type="reset" value ="다시쓰기" class="btn">
			</c:if>
			</form>
		</div>
		
	</body>
</html>