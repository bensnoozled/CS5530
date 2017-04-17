package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Feedback 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Feedback(){}
	
	public String leaveFeedback(String login , Integer choice, Integer score , String comment,  String output, Integer stage, Statement stmt, Connection con)
	{
		ResultSet rs;
		String allNonCommentedHouses="SELECT * from TH where hid not in (Select hid from Feedback where login = '"+login+"')";
		try
		{
			rs=stmt.executeQuery(allNonCommentedHouses);
			
			if(!rs.last())
			{
				System.err.println("There aren't any houses registered you haven't commented on!");
				return null;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> hids = new ArrayList<Integer>();
			
			System.out.println("Select an hid to leave feedback on");
			System.out.println();
			System.out.println("[hid] \t [category]");
			while (rs.next())
			{
				hids.add(rs.getInt("hid"));
				output += (rs.getString("hid") +"---"+ rs.getString("category") + "|"); 
			}
			
			if(stage == 0)
			{
				return output;
			}
			
			//int choice;
			//try{choice = Integer.parseInt(readInput("Enter an hid to leave feedback on"));}catch (Exception e){ System.err.println("Invalid hid input."); return;}
			
			if(hids.contains(choice))
			{
				//int score;
				//try{score = Integer.parseInt(readInput("Enter a score for this TH (from 0-10)"));}catch (Exception e){ System.err.println("Invalid score input."); return;}
				
				if(score < 0 || score > 10)
				{
					System.err.println("Invalid score input. Make sure your score was from 1-10!");
					return null;
				}
				//String text = readInput("Any comment on this TH?");
				java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
				
				String updateFeedback = "Insert into Feedback (text,fbdate,hid,login,score) values (?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(updateFeedback);
				ps.setString(1, comment);
				ps.setDate(2, date);
				ps.setInt(3, choice);
				ps.setString(4, login);
				ps.setInt(5, score);
				
				ps.executeUpdate();
				
				return "success";
			}
			else
			{
				System.err.println("You entered a hid not listed above!");
				return null;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			System.err.println("cannot execute the query");
			return null;
		}
	}
	
	public static String readInput(String message)
	{
		System.out.println(message);
		
		String temp = "";
		try
		{
			while ((temp = in.readLine()) == null && temp.length() == 0) ;
		}
		catch(Exception e)
		{
			System.out.println("Invalid Input");
		}
		return temp;
	}
}

