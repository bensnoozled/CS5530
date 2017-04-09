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

<marquee>Woop! Woop! that's the sound of the police.</marquee>
<%
String login = (String)session.getAttribute("login");
 
String choice = (String)request.getParameter("login2");
String trustR = (String)request.getParameter("trust");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || trustR == null)
{
	Connector con = new Connector();
	cs5530.Trusts trust = new cs5530.Trusts();
	
	String registeredHouses = trust.modifyTrust(login , "" , new Integer(-1) , "" , new Integer(0) , con.stmt);
	String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select a User and input a score value from -1 to 1");%> <BR> <%
	%> 
	<BR>
	<table>
		<tr>
    		<th>login</th>
 		</tr>
	<%
	for(int i = 0; i < regHouses.length; i++)
	{
		
		String[] rH = regHouses[i].split("---");
		%>
		  <tr>
		    <td><%=rH[0]%></td>
		  </tr>
		<%
	}
	
	%>
	<form action="trust.jsp">
  		<fieldset>
    		<legend></legend>
    			Login:<br>
    			<input type="text" name="login2" method=get value=<%=login%>><br>
    			Score:<br>
    			<input type="number" name="trust" method=get min=-1 max=1 value=0><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.Trusts trust = new cs5530.Trusts();
	
	String FBStatus = trust.modifyTrust(login , choice , Integer.parseInt(trustR) , "" , new Integer(1) , con.stmt);
	
	if(FBStatus != null && FBStatus.equals("success"))
	{
		%>
		<b>Successfully modified trust on user <%=choice%></b>
		<%
	}
	else
	{
		%>
		<b>Failed to modify trust on a user. Check your spelling!</b>
		<%
	}
	%>
		<BR><a href="trust.jsp"> Leave Feedback on another TH </a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>