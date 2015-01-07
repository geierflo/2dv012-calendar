package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;

import model.Calendar;
import model.UsersHasCalendar;
import model.UsersHasCalendarPK;
import ejb.CalendarEJB;

@Named
@SessionScoped
public class CalendarBean implements Serializable {

	/**
	 * 
	 */
	@EJB
	CalendarEJB calendarEJB;

	private static final long serialVersionUID = 1L;

	private String calendarName;
	private int calendarID;
	private byte public_;
	private String background;
	private Date date;

	private Part file;
	private String owner;

	public void setOwner(String name) {
		owner = name;
	}

	public String getOwner() {
		return owner;
	}

	public Part getFile() {
		return file;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public int getCalendarID() {
		return calendarID;
	}

	public void setCalendarID(int calendarID) {
		this.calendarID = calendarID;
	}

	public byte getPublic_() {
		return public_;
	}

	public void setPublic_(byte public_) {
		this.public_ = public_;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Method for creating new calendar
	public String addCalendar() {

		Calendar tmp = new Calendar();
		UsersHasCalendarPK tmp2 = new UsersHasCalendarPK();
		UsersHasCalendar tmp3 = new UsersHasCalendar();
		tmp.setCalendarname(calendarName);
		tmp.setBackground(background);
		tmp.setBegindate(date);
		tmp.setPublic_(public_);

		// If it is not public-assign it to user
		if (public_ == 0) {
			tmp2.setCalendarsCalendarId(calendarEJB.createCalendar(tmp)
					.getCalendarId());
			tmp2.setUsersUsername(owner);
			tmp3.setId(tmp2);
			System.out.println(tmp2.getCalendarsCalendarId() + " " + owner);
			calendarEJB.addUserHasCalendar(tmp3);
		}
		calendarEJB.createCalendar(tmp);
		return "home.xhtml";
	}

	public List<Calendar> allCalendars() {
		return calendarEJB.listAllCalendars();

	}

	public Calendar calendarByID() {
		return calendarEJB.getCalendarById(calendarID);
	}

	public List<Calendar> userCalendars(String username) {
		return calendarEJB.listUsersCalendars(username);

	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void upload() throws IOException {

		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("Data");

		try {
			InputStream in = file.getInputStream();
			byte[] data = new byte[in.available()];
			in.read(data);
			FileOutputStream out = new FileOutputStream(new File(path
					+ "picture" + data.hashCode() + calendarName.hashCode()
					+ ".jpeg"));
			setBackground("Datapicture" + data.hashCode()
					+ calendarName.hashCode() + ".jpeg");
			System.out.println(background);

			out.write(data);

			in.close();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Method used for setting the background of the calendar
	public String showBackground(int calID) {
		
		Calendar cal = new Calendar();
		setBackground(null);
		cal = calendarEJB.getCalendarById(calID);
		
			System.out.println(cal.getBackground() + " " + calID + "as ");
			setBackground(cal.getBackground());
		
			return "calendar.xhtml";
	}

}
