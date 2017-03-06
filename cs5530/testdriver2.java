package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;




public class testdriver2 {

	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		System.out.println("        Welcome to the UTEL System     ");
		System.out.println("1. Existing User Login");
		System.out.println("2. New User Registration:");
		System.out.println("3. exit:");
		System.out.println("please enter your choice:");
	}

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

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
				displayMenu();
				while ((choice = in.readLine()) == null && choice.length() == 0);
				try{
					c = Integer.parseInt(choice);
				}catch (Exception e)
				{

					continue;
				}
				if (c<1 | c>4)
					continue;

				if (c==1)
				{
					System.out.println("Please enter login");
					while ((login = in.readLine()) == null && login.length() == 0) ;
					System.out.println("please enter password:");
					while ((password = in.readLine()) == null && password.length() == 0) ;
					System.out.println(user.userLogin(login, password, con.stmt));
					
					if(user.userLogin(login, password, con.stmt) == null)
						c = -1;
					else
						c = 0;
				}
				else if (c == 2)
				{
					System.out.println("Please enter a UNIQUE login name");
					while ((login = in.readLine()) == null && login.length() == 0) ;
					System.out.println("please enter a password:");
					while ((password = in.readLine()) == null && password.length() == 0) ;
					System.out.println("please enter a name:");
					while ((name = in.readLine()) == null && name.length() == 0) ;
					System.out.println("please enter an address:");
					while ((address = in.readLine()) == null && address.length() == 0) ;
					System.out.println(user.createUser(login, password, name, address, con.stmt));
					
					user.userLogin(login, password, con.stmt);
					c = 0;
					
				}
				else c = -1;

				while(c >= 0)
				{
					if(c == 0)
					{
						System.out.println("Welcome " + user.getM_name());
						System.out.println("");
						System.out.println("1. Enter your own query");
						System.out.println("2. Register a new TH");
						
						while ((choice = in.readLine()) == null && choice.length() == 0 && c >= 0 && c <= 1) ;
						c = Integer.parseInt(choice);
					}
	
					switch(c)
					{
						case 1:
						{
							System.out.println("please enter your query below:");
							while ((sql = in.readLine()) == null && sql.length() == 0)
								System.out.println(sql);
							System.out.println(con.stmt.executeUpdate(sql) + " rows updated");
							//	            		 ResultSet rs=con.stmt.executeUpdate(sql);
							//	            		 ResultSetMetaData rsmd = rs.getMetaData();
							//	            		 int numCols = rsmd.getColumnCount();
							//	            		 while (rs.next())
							//	            		 {
							//	            			 //System.out.print("cname:");
							//	            			 for (int i=1; i<=numCols;i++)
							//	            				 System.out.print(rs.getString(i)+"  ");
							//	            			 System.out.println("");
							//	            		 }
							//	            		 System.out.println(" ");
							//	            		 rs.close();
							c = 0;
						}
						case 2:
						{
							String category;
							System.out.println("Please enter a TH category");
							
							while ((category = in.readLine()) == null && category.length() == 0)
								System.out.println("Please enter a TH category");
							
							System.out.println(th.createTH(user, con.stmt, category));
							
							c = 0;
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
}