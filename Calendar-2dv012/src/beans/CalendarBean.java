package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.Calendar;
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
	private UserBean userb = new UserBean();
	
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
	
	public String addCalendar(){
		Calendar tmp = new Calendar();
		
		tmp.setCalendarname(calendarName);
		tmp.setBackground(background);
		tmp.setBegindate(date);
		tmp.setPublic_(public_);
		calendarEJB.createCalendar(tmp);
		return "ListofCalendars.xhtml";
	}
	
	public List<Calendar> allCalendars(){
		return calendarEJB.listAllCalendars();
		
	}
	
	public Calendar calendarByID(){
		return calendarEJB.getCalendarById(calendarID);
	}
	
	public List<Calendar> userCalendars(){
		return calendarEJB.listUsersCalendars(userb.getUsername());
		
	}

}
