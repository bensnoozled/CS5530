
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
public class Browse {
	Browse(){};
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String m_login;
	int m_hid;
	int m_pid;
	Date m_in = new Date();
	Date m_out = new Date();
	float m_cost;
	ResultSet rs = null;

	public boolean browse(cs5530.User user, Statement stmt) {
		float low = 0;
		float high = 0;
		String address;
		String keyword;
		String category;
		int sort = 0;
		String sql = "SELECT * FROM TH T NATURAL JOIN (SELECT AVG(score) as averageFeedback, hid from Feedback group by hid) F1 NATURAL JOIN (SELECT AVG(score) as trustedAverage FROM Trust T, Feedback F where T.login2 = F.login and T.isTrusted = 1) as F2, Available A, HasKeywords H, Keywords K ";


		try{low = Float.parseFloat(readInput("Enter a low price or 0 for no limit"));}catch (Exception e){System.err.println("Invalid price input.");}
		try{high = Float.parseFloat(readInput("Enter a high price or 0 for no limit"));}catch (Exception e){System.err.println("Invalid price input.");}
		address = readInput("Enter a city, state, or enter for no preference");
		keyword = readInput("Enter a keyword or enter for no preference");
		category = readInput("Enter a category or enter for no preference");
		try{sort = Integer.parseInt(readInput("Select a sorting method:\n 1: Price\n 2: Average Feedback Score\n 3: Numerical score of Trusted feedback"));}catch (Exception e){System.err.println("Invalid sort input.");}

		if(address.length() > 0 || high > 0 || keyword.length() > 0 || category.length() > 0 || low > 0)
			sql = sql + " WHERE ";

		if(low > 0)
			sql = sql + " A.pricePerNight > " + Float.toString(low);
		if(high > 0)
		{
			if(low > 0 )
				sql = sql + " and ";
			sql = sql + " A.pricePerNight < " + Float.toString(high);
		}
		if(address.length() > 0)
		{
			if(low > 0 || high > 0)
				sql = sql + " and ";
			sql = sql + "T.address LIKE '%" + address + "%'";
		}
		if(keyword .length() > 0)
		{
			if(address.length()> 0 )
				sql = sql + " and ";
			sql = sql + " K.word ='" + keyword + "' and T.hid = H.hid";
		}
		if(category .length() > 0)
		{
			if(address.length()> 0 || keyword.length()>0)
				sql = sql + " and ";
			sql = sql + " T.category = '" + category + "'";
		}

		if(sort == 1)
			sql = sql + " ORDER BY A.pricePerNight";
		if(sort == 2)
			sql = sql + " ORDER BY averageFeedback";
		if(sort == 3)
			sql = sql + " ORDER BY trustedAverage";
		try{
			rs=stmt.executeQuery(sql);
			System.out.format("%32s  %32s  %4s  %16s  %4s  %4s  %16s  %16s  %7s  %7s\n", "Category", "Address", "pid", "pricePerNight", "hid", "wid", "Word", "language", "Average Feedback", "Trusted Average");
			System.out.println(new String(new char[170]).replace('\0', '-'));
			while (rs.next())
			{
				searchResult sr = new searchResult();
				sr.category = rs.getString("category");
				sr.address= rs.getString("address");
				sr.pid= rs.getString("pid");
				sr.pricePerNight= rs.getString("pricePerNight");
				sr.hid= rs.getString("hid");
				sr.wid= rs.getString("wid");
				sr.word= rs.getString("word");
				sr.language= rs.getString("language");
				sr.averageFeedback = rs.getString("averageFeedback");
				sr.trustedFeedback = rs.getString("trustedAverage");
				sr.print();

			}

			rs.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
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

class searchResult
		{
			public searchResult() {
			}

			String category;
			String address;
			String pid;
			String pricePerNight;
			String hid;
			String wid;
			String word;
			String language;
			String averageFeedback;
			String trustedFeedback;

			void print(){
				System.out.format("%32s  %32s  %4s  %16s  %4s  %4s  %16s  %16s  %15s  %15s%n", category, address, pid, pricePerNight, hid, wid, word, language, averageFeedback, trustedFeedback);

			}

		}