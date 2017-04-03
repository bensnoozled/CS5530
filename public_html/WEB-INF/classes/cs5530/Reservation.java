package cs5530;
import java.sql.*;
import java.util.Date;
import java.io.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ben on 3/6/17.
 */
public class Reservation{
	public Reservation(){};

	public String getM_login() {
		return m_login;
	}

	public void setM_login(String m_login) {
		this.m_login = m_login;
	}

	public int getM_hid() {
		return m_hid;
	}

	public void setM_hid(int m_hid) {
		this.m_hid = m_hid;
	}

	public String getM_start() {
		return m_start;
	}

	public void setM_start(String m_start) {
		this.m_start = m_start;
	}

	public String getM_end() {
		return m_end;
	}

	public void setM_end(String m_end) {
		this.m_end = m_end;
	}

	public int getM_pid() {
		return m_pid;
	}

	public void setM_pid(int m_pid) {
		this.m_pid = m_pid;
	}


	public float getM_cost() {
		return m_cost;
	}

	public void setM_cost(float m_cost) {
		this.m_cost = m_cost;
	}

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String m_login;
	int m_hid;
	int m_pid;
	float m_cost;
	String m_start;
	String m_end;

	public boolean createReservation(cs5530.User user, Statement stmt) {
		int hid = 0;
		int c = 1;
		try{hid = Integer.parseInt(readInput("Enter desired HID number for your reservation"));}catch (Exception e){ c = -1; }
		String output = "";
		ResultSet rs = null;
		ResultSet rs1 = null;


		String startDate = readInput("Enter desired start date.\nMust be in the format of yyyy-MM-dd");
		if(!dateValidator(startDate))
		{
			startDate = readInput("Invalid date! Please insert a valid date\nMust be in the format of yyyy-MM-dd");
		}

		String endDate = readInput("Enter desired end date.\nMust be in the format of yyyy-MM-dd");
		if(!dateValidator(startDate))
		{
			endDate = readInput("Invalid date! Please insert a valid date\nMust be in the format of yyyy-MM-dd");
		}

		String sql = "Select * from Reserve R, Period P where R.hid = P.pid and R.pid = " + hid + " and P.to >= DATE_FORMAT('" + startDate + "', '%Y-%c-%d' )AND P.from  <= DATE_FORMAT('" + endDate + "', '%Y-%c-%d') ";
		while (c == 1)
		{
			try {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					output += rs.getString("pid");
				}

				rs.close();
			} catch (Exception e) {
				System.err.println(e);
				System.out.println("cannot execute the query");
			} finally {

			}
			if (output != "") {
				try {
					c = Integer.parseInt(readInput("A reservation exists for that date. Try again?\n 1: yes\n 2:  no"));
				} catch (Exception e) {
					c = -1;
					continue;
				}
			} else {
				try {
					c = Integer.parseInt(readInput("Dates availiable! Do you wish to proceed with reservation creation? " +
							"The following reservation will be made:\n" +
							"From " + startDate + " to " + endDate + " for housing number " + hid + "\n 1: yes\n2: no"));

					String sqlCheck = "SELECT COUNT(pid) as count FROM Period WHERE `from` = DATE_FORMAT('" + startDate + "', '%Y-%c-%d' ) and `to` = DATE_FORMAT('" + endDate + "', '%Y-%c-%d')";
					rs1 = stmt.executeQuery(sqlCheck);

						rs1.next();
						String result = rs1.getString("count");
						if (Integer.parseInt(result) == 0) {
							String periodSql = "INSERT INTO `Period` (`from`, `to`) VALUES ('" + startDate + "', '" + endDate + "')";
							stmt.executeUpdate(periodSql);
						}



					String reservationSql = "INSERT INTO `5530db40`.`Reserve` (`login`, `hid`, `pid`, `cost`) VALUES ('" + user.m_login + "', 1, (SELECT pid from Period WHERE `from` = DATE_FORMAT('" + startDate + "', '%Y-%c-%d' ) and `to` = DATE_FORMAT('" + endDate + "', '%Y-%c-%d')), (SELECT pricePerNight FROM Available WHERE hid = " + hid + "));";

					if(c == 1)
					{
						int queryResult;

						queryResult=stmt.executeUpdate(reservationSql);

						if(queryResult > 0)
						{
							System.out.println("Reservation successfully made!");
							return true;
						}
						else
						{
							System.out.println("Reservation not created. Check input fields.");
							return false;
						}

					}
					else
					{
						System.out.println("Reservation cancelled");
						return false;
					}



				} catch (Exception e) {
					System.out.println("Reservation not created. Check input fields.");
					return false;
				}

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
	public String print()
	{
		return "    pid: " + this.m_pid + " hid: " + this.m_hid + " start date: " + this.m_start + " end: " + this.m_end;
	}
}
