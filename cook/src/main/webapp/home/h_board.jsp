<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>가정식 게시판</title>
		<link rel="stylesheet" href="css/board.css" type="text/css">
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
		            <li><a href="Mypage" class="mainmenu">My COOK's ☆</a></li>
		            <li><a href="InfoMain?currentPage=1">공지사항</a></li>
				</ul>
		</div>
    
    <!-- 측면 메뉴창 끝 -->

		<div id="boardList">
			<h1> 가정식's ☆ </h1>
			
	<!-- 검색창 -->
	
		<form method="post" action="SearchDetail" id="search-word" >
            <input id="main-search" type="text" name="searchHome" placeholder=" 찾고 싶은 가정식을 입력하세요">
            <input id="btn-search" type="submit" value="🔍">
        </form>
        	
    <!-- 검색창 끝   //  검색 시 가정식에서만 검색 될 수 있도록 하기!-->
    


			<div class="insert" onclick="return idcheck()">작성하기</div>

			<ul id="top">
				<li class="list">말머리</li>
				<li class="star">☆</li>
				<li class="title">제목</li>
				<li class="nik">작성자</li>
				<li class="hit">조회수</li>
				<li class="date">작성 날짜</li>
			</ul>
			<c:forEach items="${HomeMain}" var = "home">
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
	<!-- boardList 끝 -->
	
	<!-- page -->
		<div id="page">
		<ul>
			<!-- 현재페이지가 1이 아니면 -->
			<c:if test="${currentPage ne 1}">
				<c:if test='${startPage>pageBlock}'>
				<li><a href="HomeMain?currentPage=${currentPage+1-currentPage}&h_list=${h_list}">◁◁</a></li>
				<li><a href="HomeMain?currentPage=${startPage-pageBlock}&h_list=${h_list}">◀</a></li>
				</c:if>
			</c:if>
			
			
			<c:forEach begin="${startPage}" end="${endPage}" var = "i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<li><a class="P">${i}</a><li>
					</c:when>
					<c:otherwise>
						<li><a href="HomeMain?currentPage=${i}&h_list=${h_list}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>


			
			<c:if test="${currentPage lt nPage}">
				<c:if test="${endPage<nPage}">
				<li><a href="HomeMain?currentPage=${startPage+pageBlock}&h_list=${h_list}">▶</a></li>
				<li><a href="HomeMain?currentPage=${nPage}&h_list=${h_list}">▷▷</a></li>
				</c:if>
			</c:if>
		</ul>
		</div>
	<!-- page 끝-->			    
		</div>
	<script>
	function idcheck(){
		if(${sessionScope.user.u_id==null}){
			alert("로그인 후 작성할 수 있습니다.");
			return false;
		}
		if(${sessionScope.user.u_id!=null}){
			window.location.href="HomeInsert";
			return false;
		}
		return true;
	}
	</script>
	</body>
</html>