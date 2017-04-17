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

<marquee>Make a reservation</marquee>
<%
String login = (String)session.getAttribute("login");

String choice = (String)request.getParameter("HID");
String startDate = (String)request.getParameter("startDate");
String endDate = (String)request.getParameter("endDate");
String confirmation = (String)request.getParameter("confirmation");

if(login == null)
{
	response.sendRedirect("login.jsp");
}
else if(choice == null || endDate == null || startDate == null)
{
	out.println(choice);
	out.println(confirmation);
	out.println(endDate);
	out.println(startDate);
	
	Connector con = new Connector();
	cs5530.Reservation reservation  = new cs5530.Reservation();
	cs5530.Feedback feedback = new cs5530.Feedback();

	String registeredHouses = feedback.leaveFeedback(login , new Integer(-1) , new Integer(-1) , "" , "" , new Integer(0) , con.stmt, con.con);
        String[] regHouses = registeredHouses.split("\\|");
	
	out.println("Select an HID and date range to make a reservation");%> <BR> <%
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
	<form action="reserve.jsp">
  		<fieldset>
    		<legend></legend>
    			HID:<br>
    			<input type="number" name="HID" method=get><br>
    			Start Date:<br>
    			<input type="date" name="startDate" method=get><br>
    			End Date:<br>
    			<input type="date" name="endDate" method=get><br>
    		<input type="submit" value="Submit">
  		</fieldset>
	</form>
	<%
}
else if (confirmation != null)
{
	Connector con = new Connector();
	cs5530.Reservation reservation = new cs5530.Reservation();
	

	String status = reservation.confirm(login, choice, startDate, endDate, con.stmt);
	%>
	<b><%=status%></b>
	<%	
	%>
		<BR><a href="reserve.jsp"> Create another reservation </a></p>
		<%
}

else
{
	Connector con = new Connector();
	cs5530.Reservation reservation = new cs5530.Reservation();
	

	String status = reservation.createReservation(login, startDate, endDate, choice, con.stmt);

	if(status != null)
	{
		%>
			<b><%=status%></b>
			<b>To confirm click "Submit" to cancel click "Go Back"<b>
			<form action="reserve.jsp">
			<fieldset>
			<legend></legend>
			<input type="submit" value="Submit">
    			<input type="hidden" name="confirmation" value="true" method=get><br>
    			<input type="hidden" name="HID" value=<%=choice%> method=get><br>
    			<input type="hidden" name="startDate" value = <%=startDate%> method=get><br>
    			<input type="hidden" name="endDate" value=<%=endDate%> method=get><br>
		<BR><a href="reserve.jsp"> Go Back  </a></p>
			</fieldset>
			</form>
			<%
	}
	else
	{
		%>
			<b>Failed to create reservation. Please try again.</b>
			<%
	}
}
%>
</table>


<BR><BR><a href="menu.jsp"> Back to Menu </a></p>
