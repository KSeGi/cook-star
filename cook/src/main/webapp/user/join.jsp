<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>회원가입</title>
		<link rel="stylesheet" href="css/join.css" type="text/css">
		
		<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
	</head>
	<body>
		<div id="wrap">
		
		<div id="logWrap">
		    <div class="log" id="CS"><a href="index.jsp">CookStar★</a></div>
		    <div class="log"><a href="Login" id="logIn">로그인</a></div>
	    </div>
		
		<h1>회원가입</h1>
		
		<form method="post" action="Join" name="frm">
			<ul>
				<li>아이디</li>
				<li>
					<input type="text" name="u_id" class="u_id">
					<input type="button" class="btnCheck" value="중복체크">
					<font id = "checkId" size="2"></font>
				</li>
			</ul>
				
			<ul>
				<li>비밀번호</li>
				<li><input type="password" name="u_pw"></li>
			</ul>
			<ul>
				<li>비밀번호 확인</li>
				<li><input type="password" name="pw_check"></li>
			</ul>
			<ul>
				<li>이름</li>
				<li><input type="text" name="u_name"></li>
			</ul>
			<ul>
				<li>닉네임</li>
				<li><input type="text" name="u_nik"></li>
			</ul>
			<ul>
				<li>전화번호</li>
				<li>
					<input type="text" name="first" class="phone">
					-
					<input type="text" name="second" class="phone">
					-
					<input type="text" name="third" class="phone">
				</li>
			</ul>
			<div>
					<input type="submit" class="btn" value="회원가입" onclick = "return joinCheck()">
					<input type="reset" class="btn" value="초기화">
			</div>
		</form>
		<hr>
    	<b>COPYRIGHT &#169; sugi. All rights reserved.</b>
		
		</div>

	</body>
	<script>

	//아이디 중복체크
	var idck = 0
	$('.btnCheck').click(function(){
			let u_id = $('.u_id').val();
			if(u_id<5){
				alert("아이디는 4글자 이상이어야 합니다.");
				frm.u_id.focus();
			}else{
			
				$.ajax({
					type :"POST",
					data : {u_id : u_id},
					url : "IdCheck",
					success : function(result){
						if(result == 0){
							$("#checkId").html('이미 존재하는 아이디입니다.');
							$("#checkId").attr('color','red');
							idch=0;
						}else{
							$("#checkId").html('사용할 수 있는 아이디입니다.');
							$("#checkId").attr('color','green');
							//사용할 수 있는 아이디면 1 반환.
							idck = 1;
						}
					},
					error : function(){
						console.log("서버요청 실패");
					}
				})
		}
	})
	
	
	//회원가입 유효성 검사
	function joinCheck(){		
		if(document.frm.u_pw.value==""){
			alert("암호는 반드시 입력해야합니다.");
			frm.u_pw.focus();
			return false;
		}
		if(document.frm.u_pw.value!=document.frm.pw_check.value){
			alert("암호가 일치하지 않습니다.");
			frm.u_pw.focus();
			return false;
		}
		if(idck==0){
			alert("아이디 중복체크를 해주세요");
			frm.u_id.focus();
			return false;
		}
		if(document.frm.u_name.value.length==0){
			alert("이름을 입력해 주세요");
			frm.u_name.focus();
			return false;
		}
		if(document.frm.u_nik.value.length==0){
			alert("닉네임을 입력해 주세요");
			frm.u_nik.focus();
			return false;
		}
		if(document.frm.first.value.length==0){
			alert("전화번호를 입력해 주세요");
			frm.first.focus();
			return false;
		}
		if(document.frm.first.value.length!=3){
			alert("잘못된 전화번호입니다.");
			frm.first.focus();
			return false;
		}
		if(document.frm.second.value.length==0){
			alert("전화번호를 입력해 주세요");
			frm.second.focus();
			return false;
		}
		if(document.frm.second.value.length!=4){
			alert("잘못된 전화번호입니다.");
			frm.second.focus();
			return false;
		}
		if(document.frm.third.value.length==0){
			alert("전화번호를 입력해 주세요");
			frm.third.focus();
			return false;
		}
		if(document.frm.third.value.length!=4){
			alert("잘못된 전화번호입니다.");
			frm.third.focus();
			return false;
		}
		return true;
	}

	</script>
</html>