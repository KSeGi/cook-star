<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>my page 게시판</title>
		<link rel="stylesheet" href="css/mycomment.css" type="text/css">
		
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
					<li><a href="Mycomment" class="check">내가 쓴 댓글</a></li>
					<li><a href="Mywish">내가 찜한 글</a></li>
				</ul>
			</div>
			
			
			<div class="content">
				<c:forEach items="${Comment}" var = "comment" >
					<ul>
						<c:if test="${comment.getH_no() ne 0}">
						<li class="head">[가정식]</li>
						</c:if>
						<c:if test="${comment.getG_no() ne 0}">
						<li class="head">[급 식]</li>
						</c:if>
						<c:if test="${comment.getM_no() ne 0}">
						<li class="head">[밀키트]</li>
						</c:if>
						<li class="title">${comment.getTitle()}</li>
					</ul>
					<ul class="reply">
						<li class="text" ><a href="#" onclick="comhref(${comment.getH_no()},${comment.getG_no()},${comment.getM_no()})">${comment.getC_text()}</a></li>
						<li class="date">${comment.getC_joindate()}</li>
					</ul>
				</c:forEach>
			</div>
		</div>
		
		<script>
			function comhref(h_no, g_no, m_no){
				
				if(h_no != 0){
					location.href = "HomeDetail?h_no="+h_no;
				}else if(g_no !=0){
					location.href = "GeubDetail?g_no="+g_no;
				}else if(m_no !=0){
					location.href = "MealDetail?m_no="+m_no;
				}
				
			}
		
		</script>	
	
			
	</body>
</html>