<%@ page language="java" import="cs5530.*" %>
<html>
<head>
</head>
<body>

<%
String Username = (String)session.getAttribute("login");
%>
	<p>Welcome <b><%=Username%>!</b> </p>

<BR><a href="login.jsp"> Login as another User </a>
<BR><a href="favorite.jsp"> Favorite a TH</a>
<BR><BR><a href="order.jsp"> fei fei </a>