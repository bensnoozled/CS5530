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
String score = (String)request.getParameter("score");
String comment = (String)request.getParameter("comment");
if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || score == null)
{
	Connector con = new Connector();
	cs5530.Feedback feedback = new cs5530.Feedback();
	
	String registeredHouses = feedback.leaveFeedback(login , new Integer(-1) , new Integer(-1) , "" , "" , new Integer(0) , con.stmt, con.con);
	String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select an hid to leave feedback on, give it a score from 1 - 10, and leave a comment if you want :) ");%> <BR> <%
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
	<form action="feedback.jsp">
  		<fieldset>
    		<legend></legend>
    			HID:<br>
    			<input type="text" name="HID" method=get><br>
    			Score:<br>
    			<input type="text" name="score" method=get><br>
    			Comment (Optional):<br>
    			<input type="text" name="comment" method=get><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else
{
	Connector con = new Connector();
	cs5530.Feedback feedback = new cs5530.Feedback();
	
	if(comment == null)
	{
		comment = "";
	}
	
	String FBStatus = feedback.leaveFeedback(login , Integer.parseInt(choice) , Integer.parseInt(score) , comment , "" , new Integer(1) , con.stmt, con.con);
	
	if(FBStatus != null && FBStatus.equals("success"))
	{
		%>
		<b>Successfully left feedback on hid #<%=choice%></b>
		<%
	}
	else
	{
		%>
		<b>Failed to leave feedback on HID.</b>
		<%
	}
	%>
		<BR><a href="feedback.jsp"> Leave Feedback on another TH </a></p>
	<%
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>