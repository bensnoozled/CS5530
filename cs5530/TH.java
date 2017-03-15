package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class TH 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public TH(){}
	
	public boolean createTH(User usr, Statement stmt, String category)
	{
		int result;
		String sql="INSERT INTO TH (`category`, `login`) VALUES ('"+ category + "', '" + usr.m_login + "')";
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
	
	public void updateTH(User usr, Statement stmt)
	{
		ResultSet rs;
		String housesOwned="SELECT hid , category FROM TH where login = '" + usr.m_login + "' ";
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
				
				String updateHouse = "UPDATE TH SET category = '" + catReplace + "' WHERE login = '" + usr.m_login + "' AND hid = '" + choice + "';";
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
	
	public void topNMostUsefulFeedbacks(User usr, Statement stmt)
	{
		ResultSet rs;
		String housesOwned="SELECT * from TH";
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
			try{choice = Integer.parseInt(readInput("Enter an hid to inspect useful feedbacks"));}catch (Exception e){System.err.println("Invalid hid input."); return;}
			
			if(hids.contains(choice))
			{
				int topN;
				try{topN = Integer.parseInt(readInput("How many of the best feedbacks would you like?")); if(topN <= 0) throw new Exception();}catch (Exception e){System.err.println("Invalid input."); return;}
				
				String topNBestFeedbacksOnHID = "Select text , avgRating from Feedback F , (Select F1.fid , avg(R1.rating) as avgRating from Feedback F1, Rates R1 where F1.fid = R1.fid and F1.hid = "+choice+" group by F1.fid order by avgRating desc) as topNFid where F.fid = topNFid.fid limit "+topN+";";
				rs = stmt.executeQuery(topNBestFeedbacksOnHID);
				
				if(!rs.last())
				{
					System.err.println("The feedbacks for this house have never been rated!");
					return;
				}
				rs.beforeFirst();
				
				System.out.println("[text] \t [Average Rating]");
				while (rs.next())
				{
					System.out.println(rs.getString("text") +" \t "+ rs.getString("avgRating")); 
				}
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

