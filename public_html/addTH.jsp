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

<marquee>Create a new TH</marquee>
<%
String login = (String)session.getAttribute("login");

String category = (String)request.getParameter("category");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(category == null)
{
	Connector con = new Connector();
	cs5530.TH th = new cs5530.TH();
	
	out.println("Create a new TH");%> <BR> <%
	
	%>
	<form action="addTH.jsp">
  		<fieldset>
    		<legend></legend>
    			Category:<br>
    			<input type="text" name="category" method=get><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.TH th = new cs5530.TH();
	
	boolean result = th.createTH(login, con.stmt, category);
	
	if(result == true)
	{
		%>
		<b>Successfully added TH</b>
		<%
	}
	else
	{
		%>
		<b>Failed to add TH.</b>
		<%
	}
	%>
		<BR><a href="addTH.jsp"> Add another TH </a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>
