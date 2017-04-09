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

String choice = (String)request.getParameter("FID");
String score = (String)request.getParameter("score");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || score == null)
{
	Connector con = new Connector();
	cs5530.FeedbackRating feedbackRating = new cs5530.FeedbackRating();
	
	String registeredHouses = feedbackRating.rateFeedback(login , new Integer(-1), new Integer(-1), "" , 0 , con.stmt);
	String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select an fid to Rate, enter 0 for not useful , 1 for alright, 2 for excellent! Only enter a number from 0-2 into score.");%> <BR> <%
	%> 
	<BR>
	<table>
		<tr>
    		<th>fid</th>
    		<th>login</th>
    		<th>hid</th>
    		<th>comment</th>
 		</tr>
	<%
	for(int i = 0; i < regHouses.length; i++)
	{
		
		String[] rH = regHouses[i].split("---");
		%>
		  <tr>
		    <td><%=rH[0]%></td>
			<td><%=rH[1]%></td>
			<td><%=rH[2]%></td>
			<td><%=rH[3]%></td>
		  </tr>
		<%
	}
	
	%>
	<form action="feedbackRating.jsp">
  		<fieldset>
    		<legend></legend>
    			FID:<br>
    			<input type="number" name="FID" method=get value="0"><br>
    			Score:<br>
    			<input type="number" name="score" method=get value="0"><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.FeedbackRating feedbackRating = new cs5530.FeedbackRating();
	
	String FBRStatus = feedbackRating.rateFeedback(login , Integer.parseInt(choice) , Integer.parseInt(score) , "" , new Integer(1) , con.stmt);
	
	if(FBRStatus != null && FBRStatus.equals("success"))
	{
		%>
		<b>Successfully rated Feedback with fid #<%=choice%></b>
		<%
	}
	else
	{
		%>
		<b>Failed to rate feedback</b>
		<%
	}
	%>
		<BR><a href="feedbackRating.jsp"> Rate another Feedback</a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>