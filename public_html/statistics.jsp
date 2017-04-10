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
 
String choice = (String)request.getParameter("choice");
String topN = (String)request.getParameter("topN");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || topN == null)
{
	%>
	<form action="statistics.jsp">
  		<fieldset>
    		<legend>Select the Statistic type and enter the desired number of THs for the chosen statistic</legend>
    			<form name="radioButtons" method=get>
	    			<input type=radio name="choice" value=0>
	    				Most Popular THs by Category<BR>
					<input type=radio name="choice" value=1>
						Most Expensive THs by Category<BR>
					<input type=radio name="choice" value=2>
						Most Highly Rated THs by Category<BR>
				</form>
				<BR>
    			Number of THs per Category:<br>
    			<input type="number" name="topN" method=get min=1 value=1><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.Statistics stats = new cs5530.Statistics();
	
	String topNResults = stats.getStats(Integer.parseInt(choice), Integer.parseInt(topN), "",  con.stmt);
	
	if(topNResults != null && !topNResults.equals("none") && !topNResults.equals("noneR"))
	{
		if(Integer.parseInt(choice) == 0)
		{
			String[] split = topNResults.split("\\|"); 
			
			%> <b>Top <%=topN%> most popular THs for each category</b><BR> <%
			
			%> 
			<BR>
			<table>
				<tr>
		    		<th>Category</th>
		    		<th>HID</th>
		    		<th>Visit Count</th>
		 		</tr>
			<%
				for(int i = 0; i < split.length; i++)
				{
					
					String[] rH = split[i].split("---");
					%>
					  <tr>
					    <td><%=rH[0]%></td>
					    <td><%=rH[1]%></td>
					    <td><%=rH[2]%></td>
					  </tr>
					<%
				}
		}
		else if(Integer.parseInt(choice) == 1)
		{
			String[] split = topNResults.split("\\|"); 
			
			%> <b>Top <%=topN%> most expensive THs for each category</b><BR> <%
			
			%> 
			<BR>
			<table>
				<tr>
		    		<th>Category</th>
		    		<th>HID</th>
		    		<th>Average Cost</th>
		 		</tr>
			<%
				for(int i = 0; i < split.length; i++)
				{
					
					String[] rH = split[i].split("---");
					%>
					  <tr>
					    <td><%=rH[0]%></td>
					    <td><%=rH[1]%></td>
					    <td><%=rH[2]%></td>
					  </tr>
					<%
				}
		}
		else
		{
			String[] split = topNResults.split("\\|"); 
			
			%> <b>Top <%=topN%> best rated THs for each category</b><BR> <%
			
			%> 
			<BR>
			<table>
				<tr>
		    		<th>Category</th>
		    		<th>HID</th>
		    		<th>Average Score</th>
		 		</tr>
			<%
				for(int i = 0; i < split.length; i++)
				{
					
					String[] rH = split[i].split("---");
					%>
					  <tr>
					    <td><%=rH[0]%></td>
					    <td><%=rH[1]%></td>
					    <td><%=rH[2]%></td>
					  </tr>
					<%
				}
		}
	}
	else
	{
		if(topNResults == null)
		{
		%>
			<b>Unable to get results from database. Database connection issue.</b>
		<%
		}
		else
		{
			if(topNResults.equals("none"))
			{
				%>
				<b>Unable to get results. No TH has been visited by any registered categories.</b>
				<%
			}
			if(topNResults.equals("noneR"))
			{
				%>
				<b>Unable to get results. No registered categories of TH have had any THs rated.</b>
				<%
			}
		}
	}
	%>
		<BR><a href="statistics.jsp"> Get Statistics of another Type</a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>