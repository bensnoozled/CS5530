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
String FullName = (String)request.getParameter("FullName");
String Address = (String)request.getParameter("Address");
if(Username == null || Password == null || FullName == null || Address == null)
{

	if(Username != null && Username.equals("invalidCreate") && Password == null && FullName == null && Address == null)
	{
		%>
			<b>Invalid or Taken login. Ensure all fields are filled.</b>
		<% 
	}
	
%>


<form action="newUser.jsp">
  	<fieldset>
    <legend>Create a User:</legend>
    Login:<br>
    	<input type="text" name="Username" method=get><br>
    Password:<br>
    	<input type="password" name="Password" method=get><br><br>
    Full Name:<br>
    	<input type="text" name="FullName" method=get><br>
    Address:<br>
    	<input type="text" name="Address" method=get><br>	
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
	if(user.createUser(Username, Password, FullName , Address , con.stmt))
	{
		session.setAttribute("login", Username);
		response.sendRedirect("login.jsp");
	}
	else
	{
		response.sendRedirect("newUser.jsp?Username=invalidCreate");
	}
	
%>  

  <%//=order.getOrders(searchAttribute, attributeValue, connector.stmt)%> <BR><BR>

<%
// connector.closeStatement();
// connector.closeConnection();
}  // We are ending the braces for else here
%>

<BR><a href="login.jsp"> Login as another User </a></p>