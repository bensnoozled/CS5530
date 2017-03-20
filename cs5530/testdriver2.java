package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testdriver2
{

	/**
	 * @param args
	 */
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Example for cs5530");
		Connector con=null;
		String choice;
		String login = "";
		String password = "";
		String name = "";
		String address = "";
		cs5530.User user = new cs5530.User();
		cs5530.TH th = new cs5530.TH();
		cs5530.Favorites fav = new Favorites();
		cs5530.Feedback feed = new Feedback();
		cs5530.FeedbackRating fr = new cs5530.FeedbackRating();
		cs5530.Trusts trust = new cs5530.Trusts();
		cs5530.Reservation res = new cs5530.Reservation();
		cs5530.Stay stay = new cs5530.Stay();
		cs5530.Browse browse = new cs5530.Browse();
		String sql=null;
		int c=0;
		try
		{
			//remember to replace the password
			con= new Connector();
			System.out.println ("Database connection established");
			System.out.println("");
			
			while(true) //Main Loop
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
					name = readInput("please enter a name:");
					address = readInput("please enter an address:");
					
					if(user.createUser(login, password, name, address, con.stmt))
					{						
						c = 0; 
						user.userLogin(login, password, con.stmt); //User has been created successfully , log him in!
					}
					else
					{
						c = -1;
						continue;								   //Something went wrong bring user back to the login menu.
					}
									
				}
				else //Troll Input
				{
					c = -1;
					continue; 
				}

				while(c >= 0) //Login Menu Loop
				{
						displayMainMenu(user);
						
						try{c = Integer.parseInt(readInput(""));}catch (Exception e){ c = -1; continue;}
						
						if(c == 0) //Logout
						{
							break;
						}
	
					switch(c)
					{
						case 1:
						{
							sql = readInput("please enter your query below:");
							System.out.println(con.stmt.executeUpdate(sql) + " rows updated");
							break;
						}
						case 2:
						{
							String category;
							category = readInput("Please enter a TH category to create a TH");
							
							String eMessage = "Ensure all fields are entered correctly.";
							System.out.println(th.createTH(user, con.stmt, category) ? "success" : eMessage);
							break;
						}
						case 3:
						{
							th.updateTH(user, con.stmt);
							break;
						}
						case 4:
						{

							res.createReservation(user, con.stmt);

							break;
						}
						case 5:
						{
							fav.addFavorite(user, con.stmt);
							break;
						}
						case 6:
						{
							feed.leaveFeedback(user, con.stmt, con.con);
							break;
						}
						case 7:
						{
							fr.rateFeedback(user, con.stmt);
							break;
						}
						case 8:
						{
							trust.modifyTrust(user, con.stmt);
							break;
						}
						case 9:
						{
							th.topNMostUsefulFeedbacks(user, con.stmt);
							break;
						}
						case 10:
						{
							stay.addStay(user, con.stmt);
							break;
						}
						case 11:
						{
							browse.browse(user, con.stmt);
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
		System.out.println("\t3. Update a TH you own");
		System.out.println("4. Make a reservation");
		System.out.println("5. Favorite a TH");
		System.out.println("6. Leave Feedback on a TH");
		System.out.println("\t7. Leave a rating on a Feedback");
		System.out.println("8. Trust or Distrust another User.");
		System.out.println("9. Get the (N) most useful feedbacks on a TH");
		System.out.println("10. Log a stay for a reserved TH");
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