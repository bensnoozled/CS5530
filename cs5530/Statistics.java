package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Statistics 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Statistics(){}
	
	public void getStats(User usr, Statement stmt)
	{
		int listSize;
		try{listSize = Integer.parseInt(readInput("Enter the amount list size for the following statistics (m)"));}catch (Exception e){ System.err.println("Invalid list size input."); return;}
		
		ResultSet rs;
		String popTHByCategory="Select category, hid , visitCount from (SELECT V.hid, TH.Category , count(V.hid) as visitCount, @category_rank := IF(@current_category = TH.Category, @category_rank + 1, 1) AS category_count, @current_category := TH.category FROM (Select count(hid) as vc, login, hid , pid from Visit V group by login , hid, pid order by vc desc) as V join TH on (V.hid = TH.hid) group by category , hid order by visitCount desc) as sub where category_count <= "+listSize+";";
		try
		{
			rs=stmt.executeQuery(popTHByCategory);
			
			if(!rs.last())
			{
				System.out.println("No house has been visited by any registered categories.");
				return;
			}
			rs.beforeFirst();
			
			System.out.println(listSize + " most popular THs by category.");
			System.out.println();
			System.out.println("[category] \t [hid] \t [visitCount]");
			while (rs.next())
			{
				System.out.println(rs.getString("category") +" \t "+ rs.getString("hid") + " \t "+ rs.getString("visitCount")); 
			}
		}
		catch(Exception e)
		{
			//System.err.println(e.getMessage());
			System.err.println("cannot execute the query");
		}
		
		String mostExpensiveHousesByCategory="Select category, hid , averageCost from (SELECT hid, Category , averageCost, @rank := IF(@currentC = Category, @rank + 1, 1) AS category_count, @currentC := Category FROM (Select V.hid, TH.Category , avg(V.cost) as averageCost from Visit V join TH on (V.hid = TH.hid) group by category , hid order by category desc, averageCost desc) as realQ) as sub where category_count <= "+listSize+";";
		try
		{
			rs=stmt.executeQuery(mostExpensiveHousesByCategory);
			
			if(!rs.last())
			{
				System.out.println("No house has been visited by any registered categories.");
				return;
			}
			rs.beforeFirst();
			
			System.out.println(listSize + " most expensive THs by category.");
			System.out.println();
			System.out.println("[category] \t [hid] \t [averageCost]");
			while (rs.next())
			{
				System.out.println(rs.getString("category") +" \t "+ rs.getString("hid") + " \t "+ rs.getString("averageCost")); 
			}
			stmt.executeQuery("Select @rank := 1;");
		}
		catch(Exception e)
		{
			//System.err.println(e.getMessage());
			System.err.println("cannot execute the query");
		}
		
		String mostHighlyRatedTHByCategory="Select category, hid , averageScore  from (SELECT hid, Category , averageScore, @rank := IF(@currentC = Category, @rank + 1, 1) AS category_count, @currentC := Category FROM (Select V.hid, TH.Category , avg(V.score) as averageScore from Feedback V join TH on (V.hid = TH.hid) group by category, hid order by category desc , averageScore desc) as realQ) as sub where category_count <= "+listSize+";";
		try
		{
			rs=stmt.executeQuery(mostHighlyRatedTHByCategory);
			
			if(!rs.last())
			{
				System.out.println("No house has been visited by any registered categories.");
				return;
			}
			rs.beforeFirst();
			
			System.out.println(listSize + " best rated THs by category.");
			System.out.println();
			System.out.println("[category] \t [hid] \t [averageScore]");
			while (rs.next())
			{
				System.out.println(rs.getString("category") +" \t "+ rs.getString("hid") + " \t "+ rs.getString("averageScore")); 
			}
			stmt.executeQuery("Select @rank := 1;");
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			System.err.println("cannot execute the query");
		}
	}
	
	public void getUserStats(User usr, Statement stmt)
	{
		int listSize;
		try{listSize = Integer.parseInt(readInput("Enter the amount list size for the following statistics (m)"));}catch (Exception e){ System.err.println("Invalid list size input."); return;}
		
		ResultSet rs;
		String mostTrustedUsers="SELECT login2 , sum(isTrusted) as karma from Trust group by login2 order by karma desc limit "+listSize+";";
		try
		{
			rs=stmt.executeQuery(mostTrustedUsers);
			
			if(!rs.last())
			{
				System.out.println("No users have a trust rating.");
				return;
			}
			rs.beforeFirst();
			
			System.out.println(listSize + " most popular THs by category.");
			System.out.println();
			System.out.println("[login] \t [trustRating]");
			while (rs.next())
			{
				System.out.println(rs.getString("login2") +" \t "+ rs.getString("karma")); 
			}
		}
		catch(Exception e)
		{
			//System.err.println(e.getMessage());
			System.err.println("cannot execute the query");
		}
		
		String bestFeedbackUsers="Select F.login , avg(R.rating) as averageRating from (Rates R join Feedback F on R.fid = F.fid) group by F.login order by averageRating desc limit "+listSize+";";
		try
		{
			rs=stmt.executeQuery(bestFeedbackUsers);
			
			if(!rs.last())
			{
				System.out.println("No user has rated another user's feedback.");
				return;
			}
			rs.beforeFirst();
			
			System.out.println(listSize + " most popular THs by category.");
			System.out.println();
			System.out.println("[login] \t [averageFeedbackRating]");
			while (rs.next())
			{
				System.out.println(rs.getString("login") +" \t "+ rs.getString("averageRating")); 
			}
		}
		catch(Exception e)
		{
			//System.err.println(e.getMessage());
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

