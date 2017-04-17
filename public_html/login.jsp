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
    Login:<br>
    	<input type="text" name="Username" method=get><br>
    Password:<br>
    	<input type="password" name="Password" method=get><br><br>
    	<input type="submit" value="Submit">
  	</fieldset>
</form>

<marquee scrollamount=3> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=5> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=7> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=9> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=11> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=13> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=15> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=17> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=19> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=21> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=23> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=25> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=27> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=29> Woop! Woop! that's the sound of the police.</marquee>
<marquee scrollamount=31> Woop! Woop! that's the sound of the police.</marquee>

  	
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
		session.setAttribute("login", Username);
		response.sendRedirect("menu.jsp");
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

<BR><a href="newUser.jsp"> Create a User </a></p>
