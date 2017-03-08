import java.util.Date;

/**
 * Created by ben on 3/6/17.
 */
public class Reservation {
	Reservation(){};
	String m_login;
	int m_hid;
	int m_pid;
	Date m_in = new Date();
	Date m_out = new Date();
	float m_cost;
	String createReservation(cs5530.User user)
	{
		System.out.println("Please enter the desired hid");
		while ((this.m_hid = in.readLine()) == null && this.m_hid.length() == 0 && c >= 0 && c <= 1) ;
		System.out.println("Please enter the desired check-in date");
		while ((this.m_hid = in.readLine()) == null && this.m_hid.length() == 0 && c >= 0 && c <= 1) ;
		System.out.println("Please enter the desired check-out");
		while ((this.m_hid = in.readLine()) == null && this.m_hid.length() == 0 && c >= 0 && c <= 1) ;

		return "whatever";
	}
}
