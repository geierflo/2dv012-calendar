package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private boolean public_;
	private String background;
	private Date date;
	private Part file;
	private String owner;
	private int tempid;
	
	
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

	public boolean getPublic_() {
		return public_;
	}

	public void setPublic_(boolean public_) {
		this.public_ = public_;
	}

	public String getBackground() {
		
		if(this.background != null){
			System.out.println(background+ " " + calendarID);
			return background;
		}
		else{
			return "Resources/defaultBackground.jpg";
		}
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

		int tmpID = calendarEJB.createCalendar(tmp).getCalendarId();
		// If it is not public-assign it to user
		if (public_ == true) {
			tmp2.setCalendarsCalendarId(tmpID);
			tmp2.setUsersUsername(owner);
			tmp3.setId(tmp2);
			calendarEJB.addUserHasCalendar(tmp3);
		}

		return "admin.xhtml";
	}

	public List<Calendar> allCalendars() {
		return calendarEJB.listAllCalendars();

	}

	public Calendar calendarByID(int calendarID) {
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
			if(file!=null){
				InputStream in= file.getInputStream();
				byte[] data = new byte[in.available()];
				in.read(data);
				FileOutputStream out = new FileOutputStream(new File(path
						+ "picture" + data.hashCode() + calendarName.hashCode()
						+ ".jpeg"));
				setBackground("Datapicture" + data.hashCode()
						+ calendarName.hashCode() + ".jpeg");
				System.out.println(background);
				in.close();
				out.write(data);
				out.close();
			}
			else
			{
				System.out.println("No background picture chosen!");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Method used for setting the background of the calendar
	public String showBackground(int calID) {

		Calendar cal = new Calendar();
		background =null;
		cal = calendarEJB.getCalendarById(calID);
		this.calendarID=cal.getCalendarId();
		this.calendarName=cal.getCalendarname();
		this.public_=cal.getPublic_();
		this.background=cal.getBackground();
		this.date=cal.getBegindate();
		this.tempid=cal.getCalendarId();
		
		setBackground(cal.getBackground());

		return "calendar.xhtml";
	}

	public ArrayList<Calendar> getPublicCal(){
		List<Calendar> allCal = calendarEJB.listAllCalendars();
		ArrayList<Calendar> pubCal = new ArrayList<Calendar>();
		
		for(int i = 0; i<allCal.size();i++){
			
			if(allCal.get(i).getPublic_()==false){
				 pubCal.add(allCal.get(i));
				
			}
			
		}
		return pubCal;
	}

	/**
	 * method to delete a calendar, its users and days
	 * @param calId
	 * @return
	 */
	public String delete(int calId){
		System.out.println("deleting calendar "+ calId);
		calendarEJB.removeCalendarDays(calId);
		calendarEJB.removeUserHasCalendar(calId);
		calendarEJB.deleteCalendar(calId);
		return "admin.xhtml";
	}


	/**
	 * method called by editxhtml
	 * persists the bean
	 * @param dayId
	 * @return
	 */
	public String updateCalendar(){
		UsersHasCalendarPK tmp2 = new UsersHasCalendarPK();
		UsersHasCalendar tmp3 = new UsersHasCalendar();
		Calendar tmp = new Calendar();
		tmp=calendarByID(tempid);

		tmp.setCalendarId(tempid);
		tmp.setBackground(background);
		tmp.setBegindate(date);
		tmp.setCalendarname(calendarName);
		tmp.setPublic_(public_);


		if (public_ == true) {
			tmp2.setCalendarsCalendarId(tempid);
			tmp2.setUsersUsername(owner);
			tmp3.setId(tmp2);
			calendarEJB.updateCalendar(tmp);
			calendarEJB.addUserHasCalendar(tmp3);
		}
		else{
			calendarEJB.updateCalendar(tmp);
		}
		return "admin.xhtml";
	}

	/**
	 * method called from the show admin xhtml redirects to editxhtml
	 * sets the bean
	 * @param dayId
	 * @return
	 */
	public String editCalendar(int calId){
		tempid=calId;
		Calendar tmp=new Calendar();
		tmp=calendarByID(calId);
		this.background=tmp.getBackground();
		this.calendarID=tmp.getCalendarId();
		this.calendarName=tmp.getCalendarname();
		this.date=tmp.getBegindate();
		this.public_=tmp.getPublic_();

		try {
			UsersHasCalendar tmp3 = new UsersHasCalendar();
			tmp3=calendarEJB.getCalendarsUser(calId);
			this.owner=tmp3.getId().getUsersUsername();
		} catch (NullPointerException e) {
			System.out.println("No user assigned must be public");
		}

		return "editCalendar.xhtml";
	}

}
