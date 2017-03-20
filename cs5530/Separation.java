package cs5530;

import java.sql.Statement;
import java.sql.ResultSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 3/20/17.
 */
public class Separation {
	public Separation() {
	}
	ResultSet rs = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	public boolean separation(cs5530.User user, Statement stmt)
	{

		String login1 = readInput("Enter first login name");
		String login2 = readInput("Enter second login name");
		String degree1 = "SELECT Count(*) as count FROM Favorites F, Favorites F1 where F.login = '" + login1 + "' and F1.login = '" + login2 + "'and F.hid = F1.hid";
		String degree2 = "Select Count(*) as count from (Select F4.login from Favorites F Join Favorites F4 on F.hid = F4.hid Where F.login = '" + login2 + "') A, (SELECT f2.login FROM Favorites f1 JOIN Favorites f2 ON f2.hid = f1.hid where f1.login = '"+ login1 +"') A1 where A.login = A1.login";
		String result = "";
		try{
			rs=stmt.executeQuery(degree1);
			while (rs.next()) {
				result = rs.getString("count");
			}
			if(Integer.parseInt(result) == 0)
			{
				rs = null;
				rs = stmt.executeQuery(degree2);
				while (rs.next()) {
					if (Integer.parseInt(rs.getString("count")) > 0)
						System.out.println("2 degrees of separation");
					else
						System.out.println("No separation found");
			}
			}
			else
			{
				System.out.println("1 degrees of separation");
				rs = null;
				rs=stmt.executeQuery(degree2);
				while (rs.next()) {
					if (Integer.parseInt(rs.getString("count")) > 0)
						System.out.println("2 degrees of separation");
				}
			}

			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
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

		return false;
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
