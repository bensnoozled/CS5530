package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class TH 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public TH(){}
	
	public boolean createTH(String login, Statement stmt, String category)
	{
		int result;
		String sql="INSERT INTO TH (`category`, `login`) VALUES ('"+ category + "', '" + login + "')";
		try{
			result=stmt.executeUpdate(sql);
			if(result > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		catch(Exception e)
		{
			System.err.println("cannot execute the query");
			return false;
		}
	}
	
	public void updateTH(String login, Statement stmt)
	{
		ResultSet rs;
		String housesOwned="SELECT hid , category FROM TH where login = '" + login + "' ";
		try
		{
			rs=stmt.executeQuery(housesOwned);
			
			if(!rs.last())
			{
				System.err.println("You don't own any THs!");
				return;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> hids = new ArrayList<Integer>();
			
			System.out.println("[hid] \t [category]");
			while (rs.next())
			{
				hids.add(rs.getInt("hid"));
				System.out.println(rs.getString("hid") +" \t "+ rs.getString("category")); 
			}
			
			int choice;
			try{choice = Integer.parseInt(readInput("Enter an hid to modify"));}catch (Exception e){System.err.println("Invalid hid input."); return;}
			
			if(hids.contains(choice))
			{
				String catReplace = readInput("Enter the replacement category");
				
				String updateHouse = "UPDATE TH SET category = '" + catReplace + "' WHERE login = '" + login + "' AND hid = '" + choice + "';";
				stmt.executeUpdate(updateHouse);
			}
			else
			{
				System.err.println("You entered an invalid hid!");
			}
		}
		catch(Exception e)
		{
			System.err.println("cannot execute the query");
		}
	}
	
	public String topNMostUsefulFeedbacks(Integer choice ,Integer topN , String output, Integer stage , Statement stmt)
	{
		ResultSet rs;
		String housesOwned="SELECT * from TH";
		try
		{
			rs=stmt.executeQuery(housesOwned);
			
			if(!rs.last())
			{
				//System.err.println("You don't own any THs!");
				return null;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> hids = new ArrayList<Integer>();
			
			System.out.println("[hid] \t [category]");
			while (rs.next())
			{
				hids.add(rs.getInt("hid"));
				if(stage == 0)
				{					
					output+=(rs.getString("hid") +"---"+ rs.getString("category")+"|"); 
				}
			}
			
			if(stage == 0)
			{
				return output;
			}
			
			//int choice;
			//try{choice = Integer.parseInt(readInput("Enter an hid to inspect useful feedbacks"));}catch (Exception e){System.err.println("Invalid hid input."); return;}
			
			if(hids.contains(choice))
			{
				//int topN;
				//try{topN = Integer.parseInt(readInput("How many of the best feedbacks would you like?")); if(topN <= 0) throw new Exception();}catch (Exception e){System.err.println("Invalid input."); return;}
				
				String topNBestFeedbacksOnHID = "Select text , avgRating from Feedback F , (Select F1.fid , avg(R1.rating) as avgRating from Feedback F1, Rates R1 where F1.fid = R1.fid and F1.hid = "+choice+" group by F1.fid order by avgRating desc) as topNFid where F.fid = topNFid.fid limit "+topN+";";
				rs = stmt.executeQuery(topNBestFeedbacksOnHID);
				
				if(!rs.last())
				{
					System.err.println("The feedbacks for this house have never been rated!");
					return "no ratings";
				}
				rs.beforeFirst();
				
				System.out.println("[text] \t [Average Rating]");
				while (rs.next())
				{
					output+=(rs.getString("text") +"---"+ rs.getString("avgRating")+"|"); 
				}
				if(stage == 1)
				{
					return output;
				}
				return "success";
			}
			else
			{
				System.err.println("You entered an invalid hid!");
				return null;
			}
		}
		catch(Exception e)
		{
			System.err.println("cannot execute the query");
			return null;
		}
	}
	
	public String suggestTH(Integer hid , String output, Statement stmt)
	{
		ResultSet rs;
		String suggestTH="Select hid, category ,visitCount from (Select hid, count(hid) as visitCount from Visit where hid != "+hid+" and login in (Select login from Visit where hid = "+hid+") group by hid) as suggested natural join TH order by visitCount desc";
		try
		{
			rs = stmt.executeQuery(suggestTH);
			
			if(!rs.last())
			{
				System.out.println("No houses to suggest");
				return null;
			}
			rs.beforeFirst();
			
			System.out.println("(Suggestion) Have you tried reserving these THs?");
			
			System.out.println("[hid] \t [category] \t [visitCount]");
			while (rs.next())
			{
				output+=(rs.getString("hid") +"---"+ rs.getString("category") + "---" + rs.getString("visitCount")+"|"); 
			}
			return output;
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

