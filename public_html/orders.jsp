<%@ page language="java" import="cs5530.*" %>
<html>
<head>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.attributeValue.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

</script> 
</head>
<body>

<%
String searchAttribute = request.getParameter("searchAttribute");
if( searchAttribute == null ){
%>
	<p>Welcome to UTEL!</p>

	Username:
	<form name="user_search" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=hidden name="searchAttribute" value="login">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
	</form>
	<BR><BR>
	Password:
	<form name="director_search" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=hidden name="searchAttribute" value="director">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
	</form>

<%

} else {

	String attributeValue = request.getParameter("attributeValue");
//	Connector connector = new Connector();
	String login = "";
	String password = "";
	String name = "";
	String address = "";
	cs5530.User user = new cs5530.User();
	cs5530.TH th = new cs5530.TH();
	cs5530.Favorites fav = new Favorites();
	cs5530.Feedback feed = new Feedback();
	cs5530.FeedbackRating fr = new cs5530.FeedbackRating();
	cs5530.Trusts trust = new cs5530.Trusts();
	cs5530.Reservation res = new cs5530.Reservation();
	cs5530.Stay stay = new cs5530.Stay();
	cs5530.Browse browse = new cs5530.Browse();
	cs5530.Statistics stats = new Statistics();
	cs5530.Separation separation = new Separation();


%>  

  <p><b>Listing orders in JSP: </b><BR><BR>

  The orders for query: <b><%=searchAttribute%>='<%=attributeValue%>'</b> are  as follows:<BR><BR>
  <%//=order.getOrders(searchAttribute, attributeValue, connector.stmt)%> <BR><BR>
  
  <b>Alternate way (servlet method):</b> <BR><BR>
<%=out.println( searchAttribute)%>

<%
// connector.closeStatement();
// connector.closeConnection();
}  // We are ending the braces for else here
%>

<BR><a href="orders.jsp"> New query </a></p>

<p>Schema for Order table: <font face="Geneva, Arial, Helvetica, sans-serif">( 
  title varchar(100), quantity integer, login varchar(8), director varchar(10) 
  )</font></p>

<<b><%=searchAttribute%>='<%=attributeValue%>'</b>/body>
