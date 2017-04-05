<%@ page language="java" import="cs5530.*" %>
<html>
<head>
</head>
<body>

<%
String Username = (String)session.getAttribute("login");
%>
	<p>Welcome <b><%=Username%>!</b> </p>

<BR><a href="login.jsp"> Login as another User </a></p>
<br><a href="order.jsp"> fei fei </a></p>