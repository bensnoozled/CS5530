package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;




public class testdriver2
{

	/**
	 * @param args
	 */
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
		Connector con=null;
		String choice;
		String login = "";
		String password = "";
		String name = "";
		String address = "";
		cs5530.User user = new cs5530.User();
		cs5530.TH th = new cs5530.TH();
		String sql=null;
		int c=0;
		try
		{
			//remember to replace the password
			con= new Connector();
			System.out.println ("Database connection established");
			System.out.println("");
			
			while(true)
			{
				displayLoginMenu();
				
				try{c = Integer.parseInt(readInput(""));}catch (Exception e){ c = -1; continue;}
				
				if (c<0 | c>2)
					continue;

				if(c == 0) //Exit Program
				{
					con.closeConnection();
					System.out.println ("Database connection terminated");
					break;
				}
				
				if (c == 1) //Login User
				{
					login = readInput("Please enter login");
					password = readInput("please enter password:");

					c = user.userLogin(login, password, con.stmt) ? 0 : -1;
				}
				else if (c == 2) //Register User
				{
					login = readInput("Please enter a UNIQUE login name");
					password = readInput("please enter a password:");
					login = readInput("please enter a name:");
					address = readInput("please enter an address:");
					
					if(user.createUser(login, password, name, address, con.stmt))
						user.userLogin(login, password, con.stmt); //User has been created successfully , log him in!
					else
						continue;								   //Something went wrong bring user back to the login menu.
									
				}
				else //Troll Input
				{
					continue; 
				}

				while(c >= 0)
				{
					if(c == 0) //Main menu for user
					{
						displayMainMenu(user);
						
						try{c = Integer.parseInt(readInput(""));}catch (Exception e){ c = -1; continue;}
						
						if(c == 0) //Logout
						{
							break;
						}
					}
	
					switch(c)
					{
						case 1:
						{
							sql = readInput("please enter your query below:");
							System.out.println(con.stmt.executeUpdate(sql) + " rows updated");
							c = 0;
						}
						case 2:
						{
							String category;
							System.out.println("Please enter a TH category");
							
							category = readInput("Please enter a TH category");
							
							System.out.println(th.createTH(user, con.stmt, category));
							
							c = 0;
						}
						case 3:
						{

						}
						default:
						{
							System.out.println("EoM");
							con.stmt.close();
							break;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println ("Either connection error or query execution error!");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.closeConnection();
					System.out.println ("Database connection terminated");
				}

				catch (Exception e) { /* ignore close errors */ }
			}
		}
	}
	
	public static void displayLoginMenu()
	{
		System.out.println("Welcome to the UTEL System");
		System.out.println("");
		System.out.println("0. Exit");
		System.out.println("1. Existing User Login");
		System.out.println("2. New User Registration");
		System.out.println("");
	}
	
	public static void displayMainMenu(User user)
	{
		System.out.println("Welcome " + user.getM_name());
		System.out.println("");
		System.out.println("0. Logout");
		System.out.println("1. Enter your own query");
		System.out.println("2. Register a new TH");
		System.out.println("3. Make a reservation");
		System.out.println("");
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