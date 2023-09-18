<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>밀키트 게시판</title>
		<link rel="stylesheet" href="css/insert.css" type="text/css">
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
    
    
		<form method = "post" enctype = "multipart/form-data" name = "frm" action="MealInsert" id="frm">
			<div>
				<input type="text" name="m_title" placeholder="제목을 입력해주세요.">
			</div>
			<div>
				<label class="cBtn" for="img">
					<img alt="카메라" src="image/camara.jpg" class="camara" id="camara">
				</label>
				<input type ="file" name="m_img" id="img" class="img" onchange="readURL(this);">
				<img id="preview" />
			</div>	
			<div>
				<fieldset>

					<input type="radio" name="m_star" value="5" id="rate1">
					<label for="rate1">★</label>
					<input type="radio" name="m_star" value="4" id="rate2">
					<label for="rate2">★</label>
					<input type="radio" name="m_star" value="3" id="rate3">
					<label for="rate3">★</label>
					<input type="radio" name="m_star" value="2" id="rate4">
					<label for="rate4">★</label>
					<input type="radio" name="m_star" value="1" id="rate5">
					<label for="rate5">★</label>
				</fieldset>
			</div>
			<div>					
				<textarea cols="80" rows="10" name="m_text" ></textarea>
			</div>
			<div id="btn">	
				<input type="submit" value="등록" class="btn" onclick="return check()" >
				<input type="reset" value="초기화" class="btn">
			</div>
		</form>
		
	</div>
		
		
		<script>
			function check(){
				if(document.frm.m_title.value.length==0){
					alert("제목을 입력해주세요");
					frm.m_title.focus();
					return false;
				}
				return true;
			}
			
			//img미리보기
			function readURL(input) {
				  if (input.files && input.files[0]) {
				    var reader = new FileReader();
				    reader.onload = function(e) {
				      document.getElementById('preview').src = e.target.result;
				    };
				    reader.readAsDataURL(input.files[0]);
				  } else {
				    document.getElementById('preview').src = "";
				  }
			}
		</script>
	</body>
</html>