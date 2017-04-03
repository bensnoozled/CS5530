package cs5530;

import java.sql.*;

public class User {
	String m_login = "";
	String m_password = "";

	public String getM_login() {
		return m_login;
	}

	public void setM_login(String m_login) {
		this.m_login = m_login;
	}

	public String getM_password() {
		return m_password;
	}

	public void setM_password(String m_password) {
		this.m_password = m_password;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_address() {
		return m_address;
	}

	public void setM_address(String m_address) {
		this.m_address = m_address;
	}

	String m_name = "";
	String m_address = "";


	public void m_User()
	{}

	public boolean createUser(String login, String password, String name, String address, Statement stmt)
	{
		int result;
		String sql="INSERT INTO Users (`login`, `password`, `name`, `address`) VALUES ('"+ login + "', '" + password + "', '" + name + "', '" + address + "')";
		try{
			result=stmt.executeUpdate(sql);
			if(result > 0)
			{
				return true;
			}
			else
			{
				System.out.println("User not created. Check input fields.");
				return false;
			}
		}
		catch(Exception e)
		{
			System.err.println("Enter a different login, login taken.");
			return false;
		}
	}

	public boolean userLogin(String login, String password, Statement stmt)
	{
		int result;
		String sql="SELECT * FROM Users WHERE `login` = '"+ login + "' and `password` = '" + password +"';";
		String output="";
		ResultSet rs=null;
		try{
			rs=stmt.executeQuery(sql);
			
			if(!rs.last())
			{
				throw new Exception();
			}
			rs.beforeFirst();

			while (rs.next())
			{
				this.setM_login(login);
				this.setM_address(rs.getString("address"));
				this.setM_name(rs.getString("name"));
			}

			rs.close();
		}
		catch(Exception e)
		{
			System.err.println("Cannot login. Please verify login and password are valid");
			return false;
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.err.println("cannot close resultset");
			}
		}
		return true;
	}
}