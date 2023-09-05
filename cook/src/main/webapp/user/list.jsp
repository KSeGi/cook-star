<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원정보조회</title>
		<style>
			#wrap{
				width:1200px;
				margin : 0 auto;
			}
			th,td{
				border:1px solid black;
				text-align:center;
				padding:4px;
			}
			form input{
				background-color:white;
				border:none;
			}
			form input:hover{
				color:blue;
			}
		</style>
	</head>
	<body>
	<div id="wrap">
		<table>
			<tr>
				<th>번호</th>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>전화번호</th>
				<th>등급</th>
				<th>가입일</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
			<c:forEach items="${list}" var="user">
				<tr>
					<td>${user.getU_no()}</td>
					<td>${user.getU_id()}</td>
					<td>${user.getU_pw()}</td>
					<td>${user.getU_name()}</td>
					<td>${user.getU_nik()}</td>
					<td>${user.getU_tel()}</td>
					<td>${user.getU_grade()}</td>
					<td>${user.getU_joindate()}</td>
					<td>
						<form action ="Update" method ="get">
							<input type="hidden" name ="u_id" value="${user.getU_id()}">
							<input type="submit" value="수정">
						</form>
					</td>
					<td>
						<form action ="Delete" method ="post">
							<input type="hidden" name ="u_no" value="${user.getU_no()}">
							<input type="submit" value="삭제">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</body>
</html>