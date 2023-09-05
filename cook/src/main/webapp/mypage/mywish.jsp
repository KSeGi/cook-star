<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>my page 게시판</title>
		<link rel="stylesheet" href="css/mywish.css" type="text/css">
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
			
			<div class="mypageMenu">
				<ul>
					<li><a href="Mypage">내 정보</a></li>
					<li><a href="Myboard">내가 쓴 글</a></li>
					<li><a href="Mycomment">내가 쓴 댓글</a></li>
					<li><a href="Mywish" class="check">내가 찜한 글</a></li>
				</ul>
			</div>
			
			<div class="tabs">
				<input id="home" type="radio" name="tab" checked>
				<label	class="tab" for="home">가정식</label>
				<input id="geub" type="radio" name="tab">
				<label class="tab" for="geub">급식</label>
				<input id="meal" type="radio" name="tab">
				<label class="tab" for="meal">밀키트</label>
		
	<!-- my_home 시작 -->
			<div class="content" id="my_home">
				<ul class="top">
					<li class="list">말머리</li>
					<li class="star">☆</li>
					<li class="title">제목</li>
					<li class="nik">작성자</li>
					<li class="hit">조회수</li>
					<li class="date">작성 날짜</li>
				</ul>
				<c:forEach items="${HomeList}" var = "home">
					<ul>
					<c:if test="${home.getH_list()==1}">
						<li class="list">다인</li>
					</c:if>
					<c:if test="${home.getH_list()==2}">
						<li class="list">1인</li>
					</c:if>
					<c:if test="${home.getH_list()==3}">
						<li class="list">채식</li>
					</c:if>
						<li class="star">${home.getH_star()}</li>
						<li class="title"><a href="HomeDetail?h_no=${home.getH_no()}">${home.getH_title()}</a></li>
						<li class="nik">${home.getNik()}</li>
						<li class="hit">${home.getH_hit()}</li>
						<li class="date">${home.getH_joindate()}</li>
					</ul>
				</c:forEach>
			</div>
		<!-- my_home 끝-->
		
		<!-- my_geub -->
			<div class="content" id="my_geub">
				<ul class="top">
					<li class="list">말머리</li>
					<li class="star">☆</li>
					<li class="title">제목</li>
					<li class="nik">작성자</li>
					<li class="hit">조회수</li>
					<li class="date">작성 날짜</li>
				</ul>
				<c:forEach items="${GeubList}" var = "geub">
					<ul>
					<c:if test="${geub.getG_list()==1}">
						<li class="list">학식</li>
					</c:if>
					<c:if test="${geub.getG_list()==2}">
						<li class="list">직식</li>
					</c:if>
						<li class="star">${geub.getG_star()}</li>
						<li class="title"><a href="GeubDetail?g_no=${geub.getG_no()}">${geub.getG_title()}</a></li>
						<li class="nik">${geub.getG_nik()}</li>
						<li class="hit">${geub.getG_hit()}</li>
						<li class="date">${geub.getG_joindate()}</li>
					</ul>
				</c:forEach>
			</div>
		<!-- my_geub 끝-->
		
		<!-- my_meal -->
			<div class="content" id="my_meal">
				<ul class="top">
					<li class="list">말머리</li>
					<li class="star">☆</li>
					<li class="title">제목</li>
					<li class="nik">작성자</li>
					<li class="hit">조회수</li>
					<li class="date">작성 날짜</li>
				</ul>
				<c:forEach items="${MealList}" var = "meal">
					<ul>
						<li class="list">밀키트</li>
						<li class="star">${meal.getM_star()}</li>
						<li class="title"><a href="MealDetail?m_no=${meal.getM_no()}">${meal.getM_title()}</a></li>
						<li class="nik">${meal.getM_nik()}</li>
						<li class="hit">${meal.getM_hit()}</li>
						<li class="date">${meal.getM_joindate()}</li>
					</ul>
				</c:forEach>
			</div>
		<!-- my_meal 끝-->
		</div>


		</div>
	
	</body>
</html>