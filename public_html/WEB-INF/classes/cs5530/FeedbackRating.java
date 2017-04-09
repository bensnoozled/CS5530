package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FeedbackRating 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public FeedbackRating(){}
	
	public String rateFeedback(String login , Integer choice, Integer score, String output, Integer stage, Statement stmt)
	{
		ResultSet rs;
		String feedbackNotByUser="Select * from Feedback where login != '"+login+"' and fid not in (Select F.fid from Feedback F, Rates R where F.fid = R.fid and R.login = '"+login+"')";
		try
		{
			rs=stmt.executeQuery(feedbackNotByUser);
			
			if(!rs.last())
			{
				//System.err.println("There aren't any houses registered you haven't commented on!");
				return null;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> fids = new ArrayList<Integer>();
			
			System.out.println("Select an fid to rate");
			System.out.println();
			System.out.println("[fid]");
			while (rs.next())
			{
				fids.add(rs.getInt("fid"));
				output += (rs.getString("fid") +"---"+ rs.getString("login") + "---" + rs.getString("hid") + "---" + rs.getString("text")+"|");
			}
			
			if(stage == 0)
			{
				return output;
			}
			
			//int choice;
			//try{choice = Integer.parseInt(readInput("Enter an fid to rate feedback on"));}catch (Exception e){ System.err.println("Invalid hid input."); return;}
			
			if(fids.contains(choice))
			{
				//int score;
				//try{score = Integer.parseInt(readInput("Enter a score for this feedback from 0-2"));}catch (Exception e){ System.err.println("Invalid score input."); return;}
				
				if(score < 0 || score > 2)
				{
					System.err.println("Invalid score input. Make sure your score was from 0-2!");
					return null;
				}

				String updateFeedbackRating = "Insert into Rates (login,fid,rating) values ('"+login+"', "+choice+" , "+score+")";
				
				stmt.executeUpdate(updateFeedbackRating);
				
				return "success";
			}
			else
			{
				System.err.println("You entered a fid not listed above!");
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
			while ((temp = in.readLine()) == null && temp.length() == 0);
		}
		catch(Exception e)
		{
			System.out.println("Invalid Input");
		}
		return temp;
	}
}

