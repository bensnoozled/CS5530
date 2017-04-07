package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Favorites 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Favorites(){}
	
	public String addFavorite(String login , Integer choice , StringBuilder output, Integer stage, Statement stmt)
	{
		ResultSet rs;
		String allNonFavHouses="SELECT * from TH where hid not in (Select hid from Favorites where login = '"+login+"') ;";
		try
		{
			rs=stmt.executeQuery(allNonFavHouses);
			
			if(!rs.last())
			{
				System.err.println("There aren't any houses registered you haven't favorited!");
				return null;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> hids = new ArrayList<Integer>();
			
			System.out.println("Select an hid to favorite");
			System.out.println();
			System.out.println("[hid] \t [category]");
			while (rs.next())
			{
				hids.add(rs.getInt("hid"));
				output.append(rs.getString("hid") +" \t "+ rs.getString("category") + "\n"); 
			}
			
			if(stage == 0)
			{
				return output.toString();
			}
			
			//int choice;
			//try{choice = Integer.parseInt(readInput("Enter an hid to favorite"));}catch (Exception e){ System.err.println("Invalid hid input."); return;}
			
			if(hids.contains(choice))
			{
				String updateFavorites = "Insert into Favorites (hid, login) values ("+choice+", '"+ login +"')";
				stmt.executeUpdate(updateFavorites);
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
			//System.err.println(e.getMessage());
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

