package cs5530;
import java.sql.*;
import java.util.Date;
import java.io.*;
import java.lang.*;

/**
 * Created by ben on 3/6/17.
 */
public class Reservation {
	Reservation(){};
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String m_login;
	int m_hid;
	int m_pid;
	Date m_in = new Date();
	Date m_out = new Date();
	float m_cost;

	public boolean createReservation(String startDate, String endDate, int hid, Statement stmt) {
		String sql = "Select * from Reserve R, Period P where R.hid = P.pid and R.pid = " + hid + " and P.to >= DATE_FORMAT('" + startDate + "', '%Y-%c-%d' )AND P.from  <= DATE_FORMAT('" + endDate + "', '%Y-%c-%d') ";
		String output = "";
		ResultSet rs = null;
		int c = 1;

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
				try {
					if (rs != null && !rs.isClosed())
						rs.close();
				} catch (Exception e) {
					System.out.println("cannot close resultset");
				}
			}
			if (output != "") {
				try {
					c = Integer.parseInt(readInput("A reservation exists for that date. Try again?\n 1: yes\n 2: no"));
				} catch (Exception e) {
					c = -1;
					continue;
				}
			} else {
				try {
					c = Integer.parseInt(readInput("Dates availiable! Do you wish to proceed with reservation creation? " +
							"The following reservation will be made:\n" +
							"From " + startDate + " to " + endDate + " for housing number " + hid + "\n 1: yes\n2: no"));
					String periodSql = "INSERT INTO `Period` (`from`, `to`) VALUES ('" + startDate + "', '" + endDate + "');\n";
					String reservationSql = "INSERT INTO `5530db40`.`Reserve` (`login`, `hid`, `pid`, `cost`) VALUES ('test', 1, (SELECT pid from Period WHERE `from` = DATE_FORMAT('" + startDate + "', '%Y-%c-%d' ) and `to` = DATE_FORMAT('" + endDate + "', '%Y-%c-%d')), (SELECT pricePerNight FROM Available WHERE hid = " + hid + "));";


				} catch (Exception e) {
					c = -1;
					continue;
				}

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
