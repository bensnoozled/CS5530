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
<BR>
<BR><a href="favorite.jsp"> Favorite a TH</a>
<BR><a href="feedback.jsp"> Leave Feedback on a TH</a>