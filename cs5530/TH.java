package cs5530;

import java.sql.Statement;

public class TH 
{
	public TH(){}
	
	public String createTH(User usr, Statement stmt, String category)
	{
		int result;
		String sql="INSERT INTO TH (`category`, `login`) VALUES ('"+ category + "', '" + usr.m_login + "')";
		String output="";
		System.out.println("executing "+sql);
		try{
			result=stmt.executeUpdate(sql);
			if(result > 0)
				output = "success";
		}
		catch(Exception e)
		{
			output = "Failed! Please verify that you have entered valid data in all fields.";
			System.out.println("cannot execute the query");
		}

		return output;
	}
}

