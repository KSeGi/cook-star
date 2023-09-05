<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>급식 게시판</title>
		<link rel="stylesheet" href="css/detail.css" type="text/css">
		<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
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
		
		<!--  메인 -->
				<div id="detail">
					<c:forEach items="${GeubDetail}" var = "detail">
					
			        	<c:choose>
				        	<c:when test = "${sessionScope.user.u_grade==2}">
								<a href="GeubDelete?g_no=${detail.getG_no()}" class="btn">삭제</a>
				        		<a href="GeubUpdate?g_no=${detail.getG_no()}" class="btn">수정</a>
							</c:when>
							<c:when test = "${sessionScope.user.u_id==detail.getG_id()}">
								<a href="GeubDelete?g_no=${detail.getG_no()}" class="btn">삭제</a>
								<a href="GeubUpdate?g_no=${detail.getG_no()}" class="btn">수정</a>
							</c:when>
						</c:choose>
					
						<ul>							
							<c:if test="${detail.getG_list()==1}"><li class="list">학교급식</li></c:if>
							<c:if test="${detail.getG_list()==2}"><li class="list">직장급식</li></c:if>
							<li class="title">${detail.getG_title()}</li>
						</ul>
						<ul class="info">
							<c:if test="${sessionScope.user.u_id!=null }">
								<c:choose>
									<c:when test ="${w_no ne 0}">
										<li class="wish_chek"><a href ="" onclick="wish_delete(${w_no})">★</a></li>
									</c:when>
									<c:otherwise>
										<li class="wish"><a href ="" onclick="wish_like(${detail.getG_no()})">☆</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
							<li class="nik">${detail.getG_nik()}</li>
							<li class="hit">${detail.getG_hit()}</li>
							<li class="date">${detail.getG_joindate()}</li>
						</ul>
						<ul class="content">
							<c:if test ="${detail.getG_img() ne '/files/null' and detail.getG_img()!=null and detail.getG_img()!=''}">
							<li class="img"><img src="./${detail.getG_img()}"></li>
							</c:if>
							<li class="star">${detail.getG_star()}</li>
							<li class="text">
								<textarea readonly>${detail.getG_text()}</textarea>
							</li>
						</ul>
					</c:forEach>
				</div>
		<!-- 메인 끝 -->
		
		<!--  댓글  -->
			<div class="comment">
				<h2>댓글
				<span id="count">${num}</span>
				</h2>
		<!--  댓글 작성창  -->
				<div>
					<textarea id = "c_text" class="c_text" placeholder="댓글을 입력해주세요"> </textarea>	
					<input type="submit" class ="cbt" value="작성" onclick = "insertCo(${geub.g_no})">
				</div>
		<!--  댓글 목록/수정창 -->
				<c:forEach items="${Comment}" var="co" varStatus="i">
					<p>${co.getU_nik()}</p>
					<div class="cm">
					<table>
						<tr>
							<td class="reply">${co.getC_text()}</td>
							<td <c:if test="${sessionScope.user.u_no==co.getU_no()} or ${sessionScope.user.u_grade==2}"> colspan ="2"</c:if> 
								class="c_date">${co.getC_joindate()}</td>
							<c:choose>
							<c:when test = "${sessionScope.user.u_grade==2}">
								<td>
								<input type="button" class="cbtnUp" onclick="modifyView(this.id)" id="modify${i.count }" value="수정">
								<input type="button"  onclick="coDel(${co.getC_no()})"class="cbtnDe" value="삭제">
								</td>
							</c:when>
							<c:when test = "${sessionScope.user.u_no==co.getU_no()}">
								<td>
								<input type="button" class="cbtnUp" onclick="modifyView(this.id)" id="modify${i.count }" value="수정">
								<input type="button"  onclick="coDel(${co.getC_no()})" class="cbtnDe" value="삭제">
								</td>
							</c:when>
						</c:choose>
						</tr>
						
						<tr class="modifyViews">
							<td colspan="2">
							<textarea placeholder="내용을 작성하세요" name="c_textarea" class="replyModi" required="required" 
							id="c${co.getC_no()}">${co.getC_text()}</textarea></td>
							<td>
								<input type="button" class="cbtnUp" onclick="coUp(${co.getC_no()})" value="완료">
								<input type="button" class="cbtnUp" onclick="modifyCancle('modify${i.count }')" type="button" value = "취소">
							</td>
						</tr>	
					</table>

					</div>
				</c:forEach>		
			</div>			
		</div>	
	<script>
		//찜하기
		function wish_like(g_no){
			$.ajax({
				type:"POST",
				data :{g_no:g_no},
				url:"WishInsert",
				success : function(result){
					location.reload();
				}
			});
		}
		//찜 삭제
		function wish_delete(w_no){
			$.ajax({
				type:"POST",
				data :{w_no:w_no},
				url:"WishDelete",
				success : function(result){
					location.reload();
				}
			});
		}
		
		//댓글 입력
		function insertCo(g_no){
			var c_text = document.getElementById('c_text').value;
			if(${sessionScope.user.u_id==null}){
				alert("댓글 작성 권한이 없습니다. 로그인 해주세요");
			}else{
				$.ajax({
					type: "POST",
					data : {g_no : g_no, c_text : c_text},
					url: "CoInsert",
					success : function(result){
							location.reload();
					},
					error:function(){
						alert("댓글 작성를 실패하였습니다.");
					}
					
				})
			}
		}
		
		
		//댓글 삭제
		function coDel(c_no){
			$.ajax({
				type: "POST",
				data : {c_no : c_no},
				url: "CoDelete",
				success : function(result){
						location.reload();
				},
				error:function(){
					alert("댓글 삭제를 실패하였습니다.");
				}
				
			})
		}
		
		
		//댓글창 수정창 나오게 하기
		var modifyViews = document.getElementsByClassName('modifyViews')
			for (var i = 0; i < modifyViews.length; i++) {
				modifyViews[i].style.display = 'none'
			}
			function modifyCancle(idI) {
				var modifiId = document.getElementById(idI);
				modifiId.parentElement.parentElement.style.display = '';
				modifiId.parentElement.parentElement.nextElementSibling.style.display = 'none';
			}
			function modifyView(idI) {
				var modifiId = document.getElementById(idI);
				modifiId.parentElement.parentElement.style.display = 'none';
				modifiId.parentElement.parentElement.nextElementSibling.style.display = '';
			}
	
			
		//댓글창 수정
		function coUp(c_no){
			const element = document.querySelector('#c'+c_no);
			let c_text = element.value;
			console.log(c_text);
			$.ajax({
				type: "POST",
				data : {c_no : c_no, c_text:c_text},
				url: "CoUpdate",
				success : function(result){
						location.reload();
				},
				error:function(){
					alert("댓글 수정을 실패하였습니다.");
				}
				
			})
		}
	</script>	
	</body>
</html>