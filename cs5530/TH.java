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
				System.out.println("You don't own any THs!");
				return;
			}
			rs.beforeFirst();
			
			ArrayList<Integer> hids = new ArrayList<Integer>();
			
			System.out.println("hid \t category");
			while (rs.next())
			{
				hids.add(rs.getInt("hid"));
				System.out.println(rs.getString("hid") +" \t "+ rs.getString("category")); 
			}
			
			int choice;
			try{choice = Integer.parseInt(readInput("Enter an hid to modify"));}catch (Exception e){ return;}
			
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

