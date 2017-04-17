package cs5530;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Trusts 
{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Trusts(){}
	
	public String modifyTrust(String login , String choice, Integer trust , String output, Integer stage, Statement stmt)
	{
		ResultSet rs;
		String allNonFavHouses="SELECT * from Users where login != '"+login+"';";
		try
		{
			rs=stmt.executeQuery(allNonFavHouses);
			
			if(!rs.last())
			{
				System.err.println("There aren't any other users besides you that are registered!");
				return null;
			}
			rs.beforeFirst();
			
			ArrayList<String> logins = new ArrayList<String>();
			
			System.out.println("Select a User to modify your trust with. Case Sensitive.");
			System.out.println();
			System.out.println("[login]");
			
			while (rs.next())
			{
				logins.add(rs.getString("login"));
				output+=(rs.getString("login") + "|"); 
			}
			
			if(stage == 0)
			{
				return output;
			}
			
			//String choice = readInput("");
			
			if(logins.contains(choice))
			{
				//int trust;
				//try{ trust = Integer.parseInt(readInput("Enter -1 to Not Trust , 0 to stay Neutral, or 1 to Trust: " + choice)) ; if(trust < -1 || trust > 1){throw new Exception();}}catch(Exception e){System.err.println("Enter -1,0,1 next time."); return;}
				//Trust entry is already in database
				if(stmt.executeQuery("Select * from Trust where login1 = '"+login+"' and login2 = '"+choice+"'").last())
				{
					stmt.executeUpdate("Update Trust Set isTrusted = " + trust + " where login1 = '"+login+"' and login2 = '"+choice+"';");
				}
				else
				{
					String updateFavorites = "Insert into Trust(login1,login2,isTrusted) values ('"+login+"', '"+choice+"' , "+ trust +");";
					stmt.executeUpdate(updateFavorites);	
				}
				return "success";
			}
			else
			{
				System.err.println("You entered a login not listed above!");
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

