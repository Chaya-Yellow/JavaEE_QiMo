<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="java.util.*,com.store.utils.PageBean" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload=function(){
	
	document.getElementById("search").onclick=function() {
		location.href="/demoAction?action=list";
	}
	
}
</script>
</head>
<body>
	<!-- 用户增加 -->
	<form method="post" action="/demoAction">
		<input type="text" name="user_id">
		<input type="text" name="user_name">
		<input type="hidden" name="action" value="register">
		<input type="submit" value="注册"> ${msg}
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