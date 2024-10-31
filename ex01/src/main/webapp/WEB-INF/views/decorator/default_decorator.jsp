<!-- sitemesh 사용을 위한 설정 파일 -->
<!-- 작성자 : 정이나 -->
<!-- 작성일 : 2017-01-12 -->
<!-- 최종수정일 : 2024-09-05 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 개발자 작성한 title을 가져 다 사용 -->
	<title>
		JJANGPLAY:<decorator:title />
	</title>
  <!-- Bootstrap 4 + jquery 라이브러리 등록 - CDN 방식 -->
  <!-- 여기에 사용한 라이브러리들은 한번에 적용할수있음 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <!-- datepicker: jquery는 bootstrap에 정의한 라이브러리 사용 -->
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.0/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/ui/1.14.0/jquery-ui.js"></script>
  
	<!-- awesome icon 라이브러리 등록(CDN) -->
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  	<!-- google icon 라이브러리 등록(CDN) -->
  	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

	<style type="text/css">
	
	pre {
		background: white;
		border: 0px;
	}
	
	/* Remove the navbar's default margin-bottom and rounded borders */
	.navbar {
		margin-bottom: 0;
		border-radius: 0;
	}
	
	/* Add a gray background color and some padding to the footer */
	footer {
		background-color: #E4F7BA;
		padding: 25px;
	}
	
	.carousel-inner img {
		width: 100%; /* Set width to 100% */
		margin: auto;
		min-height: 200px;
	}
	
	/* Hide the carousel text when the screen is less than 600 pixels wide */
	@media ( max-width : 600px) {
		.carousel-caption {
			display: none;
		}
	}
	
	article {
		min-height: 795px;
	}
	
	#welcome {
		color: grey;
		margin: 0 auto;
	}
	
	.navbar-nav {
    margin: 0 auto; /* 수평 중앙 정렬 */
    display: flex;
    justify-content: center; /* 중앙 정렬 */
    flex-grow: 1; /* 남은 공간을 차지하도록 설정 */
	}
	
	.bg-green {
	background-color: #E4F7BA;
	}
	
	.nav-link-menu {
	font-size: 200%; /* 원하는 크기로 조절 */
    margin: 100px; /* 외부 여백 조절 */
	}
	
	</style>

	<!-- 개발자가 작성한 소스의 head 태그를 여기에 넣게 된다. title은 제외 -->
	<decorator:head/>
</head>
<body>
	<header>
		<div class="container">
		  <h2 style="color: green;"><a href="/main/main.do" style="color: green;"><i class="fa fa-envira"></i></a>맛있는 음식 재료</h2>		  
		  <ul class="nav justify-content-end">
		    <c:if test="${ empty login }">
		      <!-- 로그인을 하지 않았을 때 -->
		      <li class="nav-item">
		        <a class="nav-link" href="/member/loginForm.do">
		        <i class="fa fa-sign-in"></i>로그인</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="/member/writeForm.do">
		        <i class="fa fa-address-card-o"></i>회원가입</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="/member/searchID.do">
		        <i class="fa fa-search"></i>아이디/비밀번호 찾기</a>
		      </li>
		<%--       <c:if test="${(!empty login) && (login.gradeNo == 9) }"> --%>
		      <li class="nav-item">
		        <a class="nav-link" href="/foods/list.do">상품관리</a>
		      </li>
		<%--       </c:if> --%>
		      </c:if>
		    <c:if test="${ !empty login }">
		    <!-- 로그인 했을때 -->
		      <li class="nav-item">
		        <span class="nav-link">
		        <c:if test="${empty login.photo }">
		        	<i class="fa fa-user-circle-o"></i>
		        </c:if>
		        <c:if test="${!empty login.photo }">
		        	<img src="${login.photo }" style="width: 30px; height: 30px; border-radius: 50%;"> 
		        </c:if>
		        ${login.id }
		        </span>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="/member/logout.do">
		        <i class="fa fa-sign-out"></i>로그아웃</a>
		      </li>
		      <c:if test="${login.gradeNo == 9 }">
			      <li class="nav-item">
			        <a class="nav-link" href="/member/list.do">회원리스트보기</a>
			      </li>
		      </c:if>
		      <li class="nav-item">
		        <a class="nav-link" href="/member/view.do">내정보보기</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="/cart/list.do">장바구니</a>
		      </li>
		      </c:if>
		  </ul>
		</div>	
	</header>
	<header>
		<nav class="navbar navbar-expand-md bg-green navbar-light">
			<a class="navbar-brand" href="#"></a>
		  <!-- Toggler/collapsibe Button -->
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		
		  <!-- Navbar links -->
		  <div class="collapse navbar-collapse" id="collapsibleNavbar">
		    <ul class="navbar-nav">
		      <li class="nav-item">
		        <a class="nav-link-menu" href="/notice/list.do"><i class="fa fa-apple"></i>과일</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link-menu" href="/image/list.do">야채</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link-menu" href="/board/list.do">육류</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link-menu" href="/shop/list.do">어류</a>
		      </li>	
		    </ul>		    
		  </div>
		</nav>
	
	</header>
	<article>
		<!-- 여기에 개발자 작성한 body 태그 안에 내용이 들어온다. -->
		<decorator:body />
	</article>
	<footer class="container-fluid text-center">
		<p>이 홈페이지의 저작권은 정이나에게 있습니다.</p>
	</footer>
	
	

	  <!-- The Modal -->
	  <div class="modal fade" id="msgModal">
	    <div class="modal-dialog">
	      <div class="modal-content">
	      
	        <!-- Modal Header -->
	        <div class="modal-header">
	          <h4 class="modal-title">처리 결과 모달 창</h4>
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	        </div>
	        
	        <!-- Modal body -->
	        <div class="modal-body">
	          ${msg}
	        </div>
	        
	        <!-- Modal footer -->
	        <div class="modal-footer">
	          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	        </div>
	      </div>
	    </div>
	  </div>
	  
	<!-- session에 담은 msg를 보여주는 모달창 -->
	<c:if test="${!empty msg}">
	  <!-- 모달을 보이게하는 javascript -->
	  <script type="text/javascript">
	  	$(function () {
			$("#msgModal").modal("show");
		});
	  </script>
	  
	</c:if>
	
</body>
</html>
<%-- <%session.removeAttribute("msg");%> --%>