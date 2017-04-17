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
String topN = (String)request.getParameter("topN");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || topN == null)
{
	Connector con = new Connector();
	cs5530.TH th = new cs5530.TH();
	
	String registeredHouses = th.topNMostUsefulFeedbacks(new Integer(-1) , new Integer(-1) , "" , new Integer(0) , con.stmt);
	String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select a TH and number of top feedbacks you would like on it.");%> <BR> <%
	%> 
	<BR>
	<table>
		<tr>
    		<th>HID</th>
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
	<form action="usefulFeedback.jsp">
  		<fieldset>
    		<legend></legend>
    			HID:<br>
    			<input type="number" name="HID" method=get min=1 value=1><br>
    			Number of Feedbacks:<br>
    			<input type="number" name="topN" method=get min=1 value=1><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.TH th = new cs5530.TH();
	
	
	String topNResults = th.topNMostUsefulFeedbacks(Integer.parseInt(choice) , Integer.parseInt(topN) , "" , new Integer(1) , con.stmt);
	
	
	if(topNResults != null && !topNResults.equals("success") && !topNResults.equals("no ratings"))
	{
	
	String[] split = topNResults.split("\\|"); 
	
	%> <b>Top <%=topN%> useful feedbacks for HID #<%=choice%></b><BR> <%
	
	%> 
	<BR>
	<table>
		<tr>
    		<th>Comment</th>
    		<th>Average Rating</th>
 		</tr>
	<%
		for(int i = 0; i < split.length; i++)
		{
			
			String[] rH = split[i].split("---");
			%>
			  <tr>
			    <td><%=rH[0]%></td>
			    <td><%=rH[1]%></td>
			  </tr>
			<%
		}
	}
	else
	{
		if(topNResults == null)
		{
		%>
			<b>Unable to get results. Entered an HID not in the list.</b>
		<%
		}
		else
		{
			if(topNResults.equals("no ratings"))
			{
				%>
				<b>Unable to get results. HID entered has no feedbacks rated.</b>
				<%
			}
		}
	}
	%>
		<BR><a href="usefulFeedback.jsp"> Find useful feedbacks on another TH</a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>