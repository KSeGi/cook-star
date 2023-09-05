<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

	<head>
	    <meta charset="utf-8">
	    <title> 개인 프로젝트_CookStory</title>
	    <link rel="stylesheet" href="css/index.css" type="text/css">
	</head>

<body>
	
    <div id="wrap">
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

	
	    <!-- 상단창과 로그인 끝나는 부분 -->
	    
    <div id="introWrap">
        <div id="intro">
            <h1><em>
                    내가 먹은 다양한 요리들의
                    <span class="star">★</span>점
                </em></h1>
        </div>

        <form method="post" action="Search" id="search-word" >
            <input id="main-search" type="text" name="search" placeholder=" 찾고 싶은 음식을 입력하세요">
            <input id="btn-search" type="submit" value="🔍">
        </form>
    </div>

    <!-- 이미지와 검색창 끝나는 부분 -->

    <div id="menu">
        <ul>
            <li><a href="HomeMain?currentPage=1&h_list=0" class="mainmenu">가정식's ☆</a>
                <ul class="submenu">
                      <li><a href="HomeMain?currentPage=1&h_list=1">다인 요리's ☆</a></li>
	                  <li><a href="HomeMain?currentPage=1&h_list=2">1인 요리's ☆</a></li>
	                  <li><a href="HomeMain?currentPage=1&h_list=3">vegetarian's ☆</a></li>
                </ul>
            </li>
            <li><a href="GeubMain?currentPage=1&g_list=0" class="mainmenu">급식's ★</a>
                <ul class="submenu">
                    <li><a href="GeubMain?currentPage=1&g_list=1">학교 급식's ★</a></li>
                    <li><a href="GeubMain?currentPage=1&g_list=2">직장 급식's ★</a></li>
                </ul>
            </li>
            <li><a href="MealMain?currentPage=1" class="mainmenu">밀키트's ☆</a></li>
            <li><a href="Mypage" class="mainmenu">My COOK's ☆</a></li>
            <li><a href="InfoMain?currentPage=1" class="mainmenu">공지사항</a></li>
            
        </ul>
    </div>

    <!-- 메뉴창 끝나는 부분-->

    <!-- 집밥 best -->

    <div id="bestzip"><h1>가정식's ☆</h1></div>
    <div id="zipwrap">
        <div id="zipone">

	   	<div class="zipstar">
	   		<a href="HomeMain?currentPage=1&h_list=1">☆☆☆☆☆<br>다인 요리's ☆</a>
	   	</div>

        </div>
        <div id="ziptwo">
			<div class="zipstar">
	            <a href="HomeMain?currentPage=1&h_list=2">★★★★★<br>1인 요리's ★</a>
			</div>
        </div>
        
        <div id="zipthree">
            <div class="zipstar">
            	<a href="HomeMain?currentPage=1&h_list=3">☆☆☆☆☆<br>vegetarian's ☆</a>
            </div>  
        </div>


        <div class="zipimg">
            <img src="image/dafood.jpg">
        </div>
        <div class="zipimg">
            <img src="image/onefood.jpg">
        </div>
        <div class="zipimg">
            <img src="image/vegetarian.jpg">
        </div>
    </div>

    <!-- 급식 best -->
    
    <div id="bestsic"><h1>급식 best's ☆</h1></div>
    <div id="sicwrap">
        <div id="sicone">
            <div class="sicstar">
            <a href="GeubMain?currentPage=1&g_list=1">★★★★★<br>학교 급식's ★</a>
            </div>
        </div>
        <div id="sictwo">
        	<div class="sicstar">
            <a href="GeubMain?currentPage=1&g_list=2">★★★★★<br>직장 급식's ★</a>
       		</div>
        </div>




        <div  class="sicimg">
            <img src="image/school.jpg">
        </div>
        <div  class="sicimg">
            <img src="image/work.jpg">
        </div>
    </div>


    <!-- 밀키트 best -->

    <div id="bestmil"><h1>밀키트 best's ☆</h1></div>
    <div id="milwrap">
        <div id="milone">
            <div class="milstar"><a href="MealMain?currentPage=1">☆☆☆☆☆<br>밀키트's ☆</a>
            </div>
        </div>
        <div id="miltwo">
            <div class="milstar"><a href="MealMain?currentPage=1">☆☆☆☆☆<br>밀키트's ☆</a>
        </div>
    </div>



    	<div class="milimg">
            <img src="image/pasta.jpg">
        </div>
        <div class="milimg">
            <img src="image/milf.jpg">
        </div>
    </div>


    <!-- 하단 -->
    <hr>
    <b>COPYRIGHT &#169; sugi. All rights reserved.</b>

	</div>
</body>

</html>