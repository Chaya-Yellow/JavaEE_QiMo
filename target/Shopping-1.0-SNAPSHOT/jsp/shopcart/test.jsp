<%@ page import="com.store.domain.ShopItem" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: cccha
  Date: 2021/12/23
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


	<% HashMap<String, ShopItem> carts = (HashMap<String, ShopItem>) session.getAttribute("shopCartList");
	for (HashMap.Entry<String,ShopItem> entry :carts.entrySet())
	{%>
	<h1><%=entry.getValue().getShopAmount()%></h1>
	<h1><%=entry.getValue().getProduct().getShopPrice()%></h1>
	<%}%>

</body>
</html>
