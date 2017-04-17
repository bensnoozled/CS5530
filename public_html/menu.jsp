<%@ page language="java" import="cs5530.*" %>
<html>
<head>
</head>
<body>

<%
String Username = (String)session.getAttribute("login");
%>
	<p>Welcome <b><%=Username%>!</b> </p>


<BR><a href="addTH.jsp"> Add a TH</a>
<BR><a href="favorite.jsp"> Favorite a TH</a>
<BR>
<BR><a href="reserve.jsp"> Reserve a TH</a>
<BR>
<BR><a href="feedback.jsp"> Leave Feedback on a TH</a>
<BR><a href="feedbackRating.jsp"> Rate a Feedback on a TH</a>
<BR><a href="usefulFeedback.jsp"> Get the best Feedbacks on a TH</a>
<BR>
<BR><a href="trust.jsp"> Trust another User</a>
<BR>
<BR><a href="statistics.jsp"> Get Statistics on THs</a>
<BR>
<BR>
<BR><a href="login.jsp"> Login as another User </a>
