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
String Username = (String)request.getParameter("Username");
String Password = (String)request.getParameter("Password");
if(Username == null || Password == null){

	if(Username != null && Username.equals("invalid") && Password == null)
	{
		%>
			<b>Incorrect Username or Password</b>
		<% 
	}
	
%>
	<p>Welcome to UTEL!</p>

<form action="login.jsp">
  	<fieldset>
    <legend>Login:</legend>
    First name:<br>
    	<input type="text" name="Username" method=get><br>
    Last name:<br>
    	<input type="text" name="Password" method=get><br><br>
    	<input type="submit" value="Submit">
  	</fieldset>
</form>
  	
<%-- </form>
	Username:
	<form name="username" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
	</form>
	<BR><BR>
	Password:
	<form name="password" method=get onsubmit="return check_all_fields(this)" action="orders.jsp">
		<input type=text name="attributeValue" length=10>
		<input type=submit>
</form> --%>

<%

} 
else 
{
	Connector con = new Connector();
	cs5530.User user = new cs5530.User();
	if(user.userLogin(Username, Password, con.stmt))
	{
		response.sendRedirect("orders.jsp");
	}
	else
	{
		response.sendRedirect("login.jsp?Username=invalid");
	}
	
%>  

  <%//=order.getOrders(searchAttribute, attributeValue, connector.stmt)%> <BR><BR>

<%
// connector.closeStatement();
// connector.closeConnection();
}  // We are ending the braces for else here
%>

<BR><a href="login.jsp"> Login as another User </a></p>