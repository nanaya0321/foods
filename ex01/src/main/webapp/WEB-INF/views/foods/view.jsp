<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 정보</title>
<style type="text/css">
	#smallImageDiv img{
		width: 80px;
		height: 80px;
	}
	#smallImageDiv img:hover {
		opacity: 70%;
		cursor: pointer;
	}
	#foodsDetailDiv> div{
		padding: 10px;
		border-bottom: 1px solid #777;
	}	
</style>
<script type="text/javascript">
$(function(){
	$("#smallImageDiv img").click(function(){
// 		alert("이미지 클릭"); // 확인완료
		$("#bigImageDiv img").attr("src", $(this).attr("src"));		
	});
	
	$("#listBtn").click(function(){
// 		alert("리스트 클릭"); // 확인완료
		location="list.do?page=${param.page}" + "&perPageNum=${param.perPageNum}" + "&${foodsSearchVO.searchQuery}";
	});
	$("#updateBtn").click(function() {
// 		alert("수정 클릭"); // 확인완료
		location="updateForm.do?foods_no=${vo.foods_no}&page=${param.page}" + "&perPageNum=${param.perPageNum}" + "&${foodsSearchVO.searchQuery}";
	});
	
	$("#deleteBtn").click(function(){
		
	});
});
</script>
</head>
<body>
<div class="container">
	<div class="card">
  		<div class="card-header"><h3>상품 정보</h3></div>
  		<div class="card-body">
			<div class="row">
				<div class="col-md-6">
					<div id="smallImageDiv" >
						<c:if test="${!empty imageList }">
							<c:forEach items="${imageList }" var="imageVO">
								<img src="${imageVO.image_name }" class="img-thumbnail">									
							</c:forEach>
						</c:if>
					</div>
					<div id="bigImageDiv" class="img-thumbnail">
						<img src="${vo.image_name }" style= "width: 100%;">
					</div>
				</div>
				<div class="col-md-6" id="foodsDetailDiv">							
					<div><i class="fa fa-caret-right"></i>분류명 : ${vo.cate_name }</div>
					<div><i class="fa fa-caret-right"></i>상품 번호 : ${vo.foods_no }</div>
					<div><i class="fa fa-caret-right"></i>상품명 : ${vo.foods_name }</div>
					<div><i class="fa fa-caret-right"></i>정가 : ${vo.price }</div>
					<div><i class="fa fa-caret-right"></i>할인가 : ${vo.sale_price }</div>
					<div><i class="fa fa-caret-right"></i>배송료 : ${vo.delivery_charge }</div>
					<div><i class="fa fa-caret-right"></i>적립률 : ${vo.saved_rate }</div>
					
					<div class="input-group mb-3">
					    <div class="input-group-prepend">
					      <span class="input-group-text">사이즈</span>
					  <select class="form-control" id="size_name">
					    <option>== 사이즈 선택 ==</option>
					    <c:forEach items="${sizeList }" var="sizeVO">
						    <option>${sizeVO.size_name }</option>
					    </c:forEach>
					  </select>					    
					    </div>
					</div>
					<div class="input-group mb-3">
					    <div class="input-group-prepend">
					      <span class="input-group-text">옵션</span>
						  <select class="form-control" id="options_name">
						    <option>== 옵션 선택 ==</option>
						    <c:forEach items="${optionsList }" var="optionsVO">
							    <option>${optionsVO.options_name }</option>
						    </c:forEach>
						  </select>				    
					    </div>
					</div>														
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<pre>${vo.content }</pre>
				</div>			
			</div>
		</div>
  		<div class="card-footer">
			<button class="btn btn-primary" id="listBtn">리스트</button>
			<button class="btn btn-danger" id="deleteBtn" data-toggle="modal" data-target="#deleteModal">삭제</button>
			<button class="btn btn-success" id="updateBtn">수정</button>
		</div>
	</div>
	
	<!-- The Modal -->
	  <div class="modal fade" id="deleteModal">
	    <div class="modal-dialog modal-dialog-centered">
	      <div class="modal-content">
	      
	        <!-- Modal Header -->
	        <div class="modal-header">
	          <h4 class="modal-title">비밀번호 입력 모달창</h4>
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	        </div>
	        <!-- delete.do 로 이동시 no, pw 가 필요함 -->
	        <!-- no: hidden으로, pw: 사용자입력으로 세팅 -->
	        <form action="delete.do" method="post">
	        	<input type="hidden" name="no" value="${vo.foods_no }">
		        <!-- Modal body -->
		        
		        
		        <!-- Modal footer -->
		        <div class="modal-footer">
		          <button class="btn btn-danger">삭제</button>
		          <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
		        </div>
	        </form>	        
	      </div>
	    </div>
	  </div>
	
</div>
</body>
</html>