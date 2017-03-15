package cs5530;
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
 * Created by ben on 3/6/17.
 */
public class Stay {
	Stay(){};
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String m_login;
	int m_hid;
	int m_pid;
	Date m_in = new Date();
	Date m_out = new Date();
	float m_cost;

	public boolean addStay(cs5530.User user, Statement stmt) {
		int hid = 0;
		int c = 1;
		String selection = "";
		ResultSet rs = null;

		HashMap<String, cs5530.Reservation> reservations = new HashMap<String, cs5530.Reservation>();

		String sql = "Select * from Reserve R, Period P where login = '" + user.getM_login() + "' and R.pid = P.pid";
		System.out.println("Please select using pid from your available reservations: ");
		try{
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				cs5530.Reservation res = new cs5530.Reservation();
				res.setM_cost(Float.parseFloat(rs.getString("cost")));
				res.setM_hid(Integer.parseInt(rs.getString("hid")));
				res.setM_pid (Integer.parseInt(rs.getString("pid")));
				res.setM_start(rs.getString("from"));
				res.setM_end(rs.getString("to"));
				System.out.println(res.print());
				reservations.put(rs.getString("pid"), res);
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
		selection = readInput("Enter desired HID number for your stay");
		try
		{
			cs5530.Reservation res = new cs5530.Reservation();
			res = reservations.get(selection);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); //compute cost
			Date startDate = format.parse(res.getM_start());
			Date endDate = format.parse(res.getM_end());
			long diff = endDate.getTime() - startDate.getTime();
			long numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//info on this from here http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
			float totalCost = numDays * res.getM_cost();
			sql = "INSERT INTO `5530db40`.`Visit` (`login`, `hid`, `pid`, `cost`) VALUES ('" + user.getM_login() + "', " + res.getM_hid() + ",  " + res.getM_pid() + ", " + String.valueOf(totalCost) + ");";
			try{
				int result;
				result=stmt.executeUpdate(sql);
				if(result > 0)
				{
					System.out.println("Stay successfully recorded");
					return true;
				}
				else
				{
					System.out.println("Unable to log your stay! Please try again. ");
					return false;
				}
			}
			catch(Exception e)
			{
				System.err.println("Cannot execute the query.");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Unable to log your stay! Please try again. ");
		}

		return false;
	}
	public static boolean dateValidator(String date)
	{
		String pattern = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(date);

		if(m.find())
			return true;
		else
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
