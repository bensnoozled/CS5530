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
		 System.out.println("        Welcome to the UTrack System     ");
    	 System.out.println("1. Register a new user");
    	 System.out.println("2. enter your own query:");
    	 System.out.println("3. exit:");
    	 System.out.println("pleasse enter your choice:");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
		Connector con=null;
		String choice;
        String cname;
        String dname;
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
	            	 if (c<1 | c>15)
	            		 continue;

					 switch(c)
					 {
					 	case 1:
							{
								String login = "";
								String password = "";
								String name = "";
								String address = "";

									System.out.println("Please enter a UNIQUE login name");
									while ((login = in.readLine()) == null && login.length() == 0) ;
									System.out.println("please enter a password:");
									while ((password = in.readLine()) == null && password.length() == 0) ;
									System.out.println("please enter a name:");
									while ((name = in.readLine()) == null && name.length() == 0) ;
									System.out.println("please enter an address:");
									while ((address = in.readLine()) == null && address.length() == 0) ;
									cs5530.User user = new cs5530.User();
									System.out.println(user.createUser(login, password, name, address, con.stmt));
									break;
							}

						 case 2:
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
