<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
		<script type="text/javascript">
window.onload=function(){
	
	document.getElementById("btn_cart").onclick=function() {
		location.href="/demoAction?action=my_shopcart";
	}
}
</script>
	</head>

	<body>

<%--<%@ include file="/jsp/header.jsp" %>--%>

		<div class="container">
		<input type="button" value="我的购物车" id="btn_cart"> 
		
				 <div class="col-md-12">购物车中暂无数据,赶紧剁手去吧!</div>
			
			

			<div class="row">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
						  <c:forEach items="${shopCartList }" var="item">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="pid" value="22">
									<img src="${item.value.product.pImage }" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">Pname</a>
								</td>
								<td width="20%">
									￥
								</td>
								<td width="10%">
									<input type="text" name="quantity" value="${item.key }" maxlength="4" size="10">
								</td>
								<td width="15%">
									<span class="pName"><c:out value="${item.value.product.pname }" /></span>
								</td>
								<td>
									<%-- <a href="javascript:void(0);" class="delete" onclick="delCart(${item.product.pid})">删除</a> --%>
									<input type="hidden" name="pid" value="${item.value.product.pid}"/>
									<a href="javascript:void(0);" title="${item.value.product.pid}" class="delete" id="${item.value.product.pid}">删除</a> 
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;"></em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥元</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="javascript:void(0)" id="clear" class="clear">清空购物车</a>
					
					<a href="">
						<%--提交表单 --%>
						<input type="button" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>
				
			
			
			
		</div>

<%--<%@ include file="/jsp/footer.jsp" %>--%>

	</body>
<script>
function delCart(id){
	if(confirm("忍心删除我吗?")){
		location.href="";
	}
}


$(function(){
	$("#clear").click(function(){
		if(confirm("忍心删除我吗?")){
			location.href="";
		}
	});
	
	
	$(".delete").click(function(){
		var pid=this.title;
		if(confirm("忍心删除我吗?")){
			location.href="";
		}
	});
	
});
</script>
</html>