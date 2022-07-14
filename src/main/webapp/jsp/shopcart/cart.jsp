<%@ page import="java.util.Map" %>
<%@ page import="com.store.domain.ShopItem" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>我的购物车</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
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
					alert('test');
					location.href="/JavaEE_QiMo/cartAction?action=my_shopcart";
				}
			}
			function fget(){
				alert('111')
				location.href="/JavaEE_QiMo/cartAction?action=my_shopcart";
			}
		</script>
	</head>

	<body>

	<% HashMap<String, ShopItem> carts = (HashMap<String, ShopItem>) session.getAttribute("shopCartList");

	for (HashMap.Entry<String,ShopItem> entry :carts.entrySet())
	{%>
	<h1><%=entry.getValue().getShopAmount()%></h1>
	<h1><%=entry.getValue().getProduct().getShopPrice()%></h1>



<%@ include file="/jsp/header.jsp" %>

		<div class="container">
<%--				<c:if test="${empty shopCartList}">--%>
<%--					<div class="col-md-12">购物车中暂无数据,赶紧剁手去吧!</div>--%>
<%--				</c:if>--%>

<%--			<input type="button" value="我的购物车" id="btn_cart">--%>
			<input type="button" value="我的购物车" id="btn_cart" onclick="fget()">
			<input type="submit" value="生成订单" id="btn_create_order"> <span class="orderMsg">${order_msg }</span>
			<br>
<%--			<c:if test="${not empty shopCartList}">--%>

				<input type="hidden" name="action" value="create_order">
				<div class="shopCart">
					<ul>
<%--						<c:forEach var="item" items="${shopCartList}" varStatus="vs">--%>
							<li>
							<li>
								<input type="checkbox" name="shopChk" value="<%=entry.getKey()%>">
								<img src="${item.value.product.pImage }">
								<a href="">
									<span class="pName"><%=entry.getValue().getProduct().getPname()%></span>
								</a>
								<span class="uPrice"><%=entry.getValue().getProduct().getShopPrice()%>元</span>&nbsp;&nbsp;
								购买数量：<input type="number" min="0" max="99" name="shopNum"/>
							</li>
<%--						</c:forEach>--%>
					</ul>
				</div>
<%--			</c:if>--%>

			<div class="row">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>勾选</th>
								<th>图片</th>
								<th>商品</th>
								<th>单价</th>
								<th>数量</th>
								<th>金额</th>
								<th>操作</th>
							</tr>

<%--							<c:forEach var="item" items="${shopCartList }" varStatus="vs">--%>
							<tr class="active">
								<li>
									<input type="checkbox" name="shopChk" value="<%=entry.getKey()%>>">
								</li>

								<li width="60" width="40%">
<%--									<input type="hidden" name="id" value="22">--%>
									<img src="../../<%=entry.getValue().getProduct().getpImage()%>" width="70" height="60">
								</li>

<%--								<td>--%>
<%--									<input type="checkbox" name="shopChk" value="${item.key}>--%>
<%--								</td>--%>

<%--								<li width="40%">--%>
<%--									<a target="_blank">Empty</a>--%>
<%--								</li>--%>
								<li width="60" width="40%">
									<%--									<input type="hidden" name="id" value="22">--%>
									<span class="pName"><c:out value="<%=entry.getValue().getProduct().getPname()%>" /></span>
								</li>

								<li width="20%">
									￥<span class="uPrice"><%=entry.getValue().getProduct().getMarketPrice()%>元</span>
									￥<span class="uPrice"><%=entry.getValue().getProduct().getShopPrice()%>元</span>&nbsp;&nbsp;
								</li>
								<li width="10%">
<%--									<a href="#" class="J_Minus no-minus">-</a>--%>
<%--									<input type="text" name="quantity" value="${item.value.product.quantity}" maxlength="4" size="10">--%>
<%--									<a href="#" class="J_Plus plus">+</a>--%>
									<input type="number" min="0" max="99" name="shopNum"/>
								</li>
<%--								<td width="15%">--%>
<%--									<span class="subtotal"<c:out value="${item.value.product.pname }" />>￥${item.value.product.shop_price}</span>--%>
<%--								</td>--%>
								<li width="10%">
									<input type="text" name="amountPrice" value="<%=entry.getValue().getShopAmount()%>*<%=entry.getValue().getProduct().getShopPrice()%>" maxlength="4" size="10">
								</li>

								<li>
									<%-- <a href="javascript:void(0);" class="delete" onclick="delCart(${item.product.pid})">删除</a> --%>
									<input type="hidden" name="pid" value="<%=entry.getValue().getProduct().getPid()%>"/>
									<a href="javascript:void(0);" title="<%=entry.getValue().getProduct().getPid()%>" class="delete" id="<%=entry.getValue().getProduct().getPid()%>">删除</a>
								</li>
							</tr>
<%--							</c:forEach>--%>

<%--							<c:forEach var="item" items="${shopCartList }" varStatus="vs">--%>
<%--								<li>--%>
<%--									<input type="checkbox" name="shopChk" value="${item.key }">--%>
<%--									<img src="${item.value.product.pImage }">--%>
<%--									<a href="">--%>
<%--										<span class="pName"><c:out value="${item.value.product.pname }" /></span>--%>
<%--									</a>--%>
<%--									<span class="uPrice"><c:out value="${item.value.product.shopPrice }" />元</span>&nbsp;&nbsp;--%>
<%--									购买数量：<input type="number" min="0" max="99" name="shopNum"/>--%>
<%--								</li>--%>
<%--							</c:forEach>--%>
	
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:left;float: left">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;"></em>&nbsp;<br>
				</div>
				<div style="text-align: right">
					商品金额: <strong style="color:#ff6600;">元</strong>
				</div>

				<div style="text-align:left;margin-top:10px;margin-bottom:10px;float: left">
					<a href="javascript:void(0)" id="clear" class="clear">清空购物车</a>
					
<%--					<a href="">--%>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;float:right">
					<a href="">
						<%--提交表单 --%>
						<input type="submit" class="btn_create_order btn-warning" value="提交订单" name="submit"><span class="orderMsg">${order_msg }</span>
					</a>
				</div>


			</div>
				
			
	
		</div>



	<%}%>
<%@ include file="/jsp/footer.jsp" %>

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