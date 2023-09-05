<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이 페이지</title>
	<link rel="stylesheet" href="css/mypage.css" type="text/css">
	</head>
	<body>
		<div id=wrap>
		 <div id="logWrap">
	        <div class="log" id="CS"><a href="index.jsp">CookStar★</a></div>
		    <form method="post" action="Search" id="search">
	            <input id="search_text" name="search" type="text">
	            <input id="header_btn" type="submit" value="🔍">
	        </form>
        
        	<c:if test = "${sessionScope.user.u_id==null }">
	        	<div class="log"><a href="Join" id="SignUp">회원가입</a></div>
	        	<div class="log"><a href="Login" id="logIn">로그인</a></div>
	        </c:if>
	        <c:if test = "${sessionScope.user.u_id!=null }">
	        	<c:if test = "${sessionScope.user.u_grade==2}">
	        		<div class="log"><a href="List" id="list">회원조회</a></div>
	        	</c:if>
	        	<div class="log"><a href="Logout" id="Logout">로그아웃</a></div>
	        </c:if>
	    </div>
	    
	   <!-- 상단바 부분 끝 -->
	
	   
		<div id="menu">
			<section class="iconbox">
		    <section class="icon">
				<div></div>
				<div></div>
				<div></div>
			</section>
			</section>
			  
				<ul>
					<li><a href="HomeMain?currentPage=1&h_list=0">가정식's ☆</a>
		            	<ul>
		                    <li><a href="HomeMain?currentPage=1&h_list=1">다인 요리's ☆</a></li>
		                    <li><a href="HomeMain?currentPage=1&h_list=2">1인 요리's ☆</a></li>
		                    <li><a href="HomeMain?currentPage=1&h_list=3">vegetarian's ☆</a></li>
		                </ul>
	           		</li>
		            <li><a href="GeubMain?currentPage=1&g_list=0">급식's ★</a>
		                <ul>
		                    <li><a href="GeubMain?currentPage=1&g_list=1">학교 급식's ★</a></li>
		                    <li><a href="GeubMain?currentPage=1&g_list=2">직장 급식's ★</a></li>
		                </ul>
		            </li>
		            <li><a href="MealMain?currentPage=1">밀키트's ☆</a></li>
		            <li><a href="Mypage">My COOK's ★</a></li>
		            <li><a href="InfoMain?currentPage=1">공지사항</a></li>
				</ul>
		</div>
    
    <!-- 측면 메뉴창 끝 -->
			<h1>My page</h1>
			<div class="tabs">
			<ul>
				<li><a href="Mypage"  class="check">내 정보</a></li>
				<li><a href="Myboard">내가 쓴 글</a></li>
				<li><a href="Mycomment">내가 쓴 댓글</a></li>
				<li><a href="Mywish">내가 찜한 글</a></li>
			</ul>
				
				
				<!-- my_info 시작 -->
				<div class="content" id="my_info">
					<div>
						<p class="first">ID</p><span class="first">${sessionScope.user.u_id}</span>
					</div>
					<div>
						<p>PW</p><span>${sessionScope.user.u_pw}</span>
					</div>
					<div>
						<p>이름</p><span>${sessionScope.user.u_name}</span>
					</div>
					<div>
						<p>닉네임</p><span>${sessionScope.user.u_nik}</span>
					</div>
					<div>
						<p>전화번호</p><span>${sessionScope.user.u_tel}</span>
					</div>
					<div>
						<p>가입날짜</p><span>${sessionScope.user.u_joindate}  </span>
					</div>
					<form action ="Update" method ="get">
						<input type="hidden" name ="u_id" value="${sessionScope.user.u_id}">
						<input type="submit" value="수정">
					</form>
					<form action ="Delete" method ="post">
						<input type="hidden" name ="u_no" value="${sessionScope.user.u_no}">
						<input type="submit" value="삭제">
					</form>
				</div>
		<!-- my_info 끝-->
			</div>
		<!-- tabs 끝 -->
		
			
			
			
		</div>
				


</body>
</html>