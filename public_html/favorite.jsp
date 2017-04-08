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

String choice = (String)request.getParameter("HID");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null)
{
	Connector con = new Connector();
	cs5530.Favorites favorites = new cs5530.Favorites();
	
	String registeredHouses = favorites.addFavorite(login, -1, "" , 0 , con.stmt);
	String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select an hid to favorite.");%> <BR> <%
	%> 
	<BR>
	<table>
		<tr>
    		<th>hid</th>
    		<th>category</th>
 		</tr>
	<%
	for(int i = 0; i < regHouses.length; i++)
	{
		
		String[] rH = regHouses[i].split("---");
		%>
		  <tr>
		    <td><%=rH[0]%></td>
			<td><%=rH[1]%></td>
		  </tr>
		<%
	}
	
	%>
	<form action="favorite.jsp">
  		<fieldset>
    		<legend></legend>
    			HID:<br>
    			<input type="text" name="HID" method=get><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.Favorites favorites = new cs5530.Favorites();
	
	String favTHStatus = favorites.addFavorite(login, Integer.parseInt(choice), "", 1 , con.stmt);
	
	if(favTHStatus != null && favTHStatus.equals("success"))
	{
		%>
		<b>Successfully favorited hid #<%=choice%></b>
		<%
	}
	else
	{
		%>
		<b>Failed to favorite HID.</b>
		<%
	}
	%>
		<BR><a href="favorite.jsp"> Favorite another TH </a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>