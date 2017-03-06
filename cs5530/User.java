package cs5530;

import java.sql.*;

public class User {
	public User()
	{}

	public String createUser(String login, String password, String name, String address, Statement stmt)
	{
		int result;
		String sql="INSERT INTO Users (`login`, `password`, `name`, `address`) VALUES ('"+ login + "', '" + password + "', '" + name + "', '" + address + "')";
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

	public String userLogin(String login, String password, Statement stmt)
	{
		int result;
		String sql="SELECT * FROM Users WHERE login = "+ login + " and password = " + password +";";
		String output="";
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				output+=rs.getString("name")+"   "+rs.getString("address")+"\n";
			}

			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("Cannot login. Please verify login and password are valid");
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}