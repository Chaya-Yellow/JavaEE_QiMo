<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="java.util.*,com.store.utils.PageBean" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
ul,li{
	list-style: none;
}
ul img{
	width: 50px;
	height: 50px;
}
.pName{
	font: 14px/50px;
	font-weight: bold;
}
.uPrice{
	font: 12px/50px;
	font-weight: bold;
	color: red;
}
.shopCart{
	width:800px;
	height: 500px;
	overflow: auto;
	font-size: 12px;
}
.orderMsg{
	font-size: 14px;
	font-weight:bold;
	color: red;
}
</style>
<script type="text/javascript">
window.onload=function(){
	
	document.getElementById("search").onclick=function() {
		location.href="/JavaEE_QiMo/demoAction?action=list";
	}
	
	document.getElementById("btn_cart").onclick=function() {
		location.href="/JavaEE_QiMo/demoAction?action=my_shopcart";
	}
}
</script>
</head>
<body>
	<!-- 用户增加 -->
	<h1>用户注册例子</h1>
	<form method="post" action="/demoAction">
		<input type="text" name="user_id">
		<input type="text" name="user_name">
		<input type="hidden" name="action" value="register">
		<input type="submit" value="注册"> ${msg}
	</form>
	
	<br>
	<h1>购物车例子</h1>
	<form method="post" action="/demoAction">
	<input type="button" value="我的购物车" id="btn_cart"> 
	<input type="submit" value="生成订单" id="btn_create_order"> <span class="orderMsg">${order_msg }</span>
	<br>
	<c:if test="${not empty shopCartList}">
		
		<input type="hidden" name="action" value="create_order">
	<div class="shopCart">	
		<ul>
		<c:forEach var="item" items="${shopCartList }" varStatus="vs">
			<li>
			<input type="checkbox" name="shopChk" value="${item.key }">
			<img src="${item.value.product.pImage }">
			<a href="">
			<span class="pName"><c:out value="${item.value.product.pname }" /></span>
			</a>
			<span class="uPrice"><c:out value="${item.value.product.shopPrice }" />元</span>&nbsp;&nbsp;
			购买数量：<input type="number" min="0" max="99" name="shopNum"/>
			</li>
		</c:forEach>
		</ul>
		</div>
	</c:if>
	</form>
	<br>
	<h2>查詢列表的例子</h2>

	<%-- ${page.list}  --%> 

<c:if test="${empty page.list}">
				<div class="col-md-2">
					<h1>暂无数据</h1>
				</div>
			</c:if>
			<c:if test="${not empty page.list}">
				<c:forEach items="${page.list}" var="p">
					<div class="col-md-2">
						<a href="">
							<img src="${pageContext.request.contextPath}/${p.pimage}" width="170" height="170" style="display: inline-block;">
						</a>
						<p><a href="" style='color:green'>${p.pname}</a></p>
						<p><font color="#FF0000">商城价：&yen;${p.shop_price}</font></p>
					</div>
				</c:forEach>
				<jsp:include page="${pageContext.request.contextPath}/jsp/pageFile.jsp"></jsp:include>
			</c:if>

	
	<input type="button" value="查詢商品" id="search">
</body>
</html>