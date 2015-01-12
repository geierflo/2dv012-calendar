package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Day;
import ejb.DayEJB;

@Named
@SessionScoped
public class DayBean implements Serializable {

	/**
	 * 
	 */
	@EJB
	static
	DayEJB dayEJB;
	
	private static final long serialVersionUID = 1L;
	
	private int iddays;
	private Date date;
	private String text;
	private String link;
	private int calendars_calendar_id;
	private int tempid;
	
	public int getTempid() {
		return tempid;
	}

	public void setTempid(int tempid) {
		this.tempid = tempid;
	}

	public int getIddays() {
		return iddays;
	}

	public void setIddays(int iddays) {
		this.iddays = iddays;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		if(link.contains("watch?v="))
			link=link.replace("watch?v=", "v/");
		this.link = link;
	}
	

	public int getCalendars_calendar_id() {
		return calendars_calendar_id;
	}

	public void setCalendars_calendar_id(int calendars_calendar_id) {
		this.calendars_calendar_id = calendars_calendar_id;
	}

	
	public String addDay(){
		Day tmp = new Day();
		
		tmp.setIddays(iddays);
		tmp.setDate(date);
		tmp.setText(text);
		tmp.setLink(link);
		tmp.setCalendars_calendar_id(calendars_calendar_id);
		List<model.Calendar> temp =dayEJB.listAllCalendars();
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).getCalendarId()==calendars_calendar_id){
				dayEJB.createDay(tmp);
				showCalendarDays(calendars_calendar_id);
				return "ShowCalendarDays.xhtml";
			}
		}
		System.out.println("No caledar with this ID "+calendars_calendar_id+" in database");
		FacesContext.getCurrentInstance().addMessage("myForm:calId", new FacesMessage("No caledar with this ID in database", "No caledar with this ID in database"));
		return"";
	}
	
	public List<Day> allDays(){
		return dayEJB.listAllDays();
		
	}
	
	public Day dayByID(int iddays){
		return dayEJB.getDayById(iddays);
	}
	
	/**
	 * method to save in bean to display all days
	 * @param calendarId
	 * @return
	 */
	public String showCalendarDays(int calendarId){
		this.tempid=calendarId;
		return "ShowCalendarDays.xhtml"; 
	}
	
	
	public List<Day> calendarDays(){
		return dayEJB.listCalendarDays(tempid);
	}
	
	public List<Day> calendarDays(int id){
		if(dayEJB.listCalendarDays(id)==null){
			return null;}
		return dayEJB.listCalendarDays(id);
	}
	
	public String delete(int dayId){
		dayEJB.deleteDay(dayId);
		return "admin.xhtml";
	}
	
	/**
	 * method called by edit xhtml
	 * persists the bean
	 * @param dayId
	 * @return
	 */
	public String updateDay(){
		Day tmp = new Day();
		tmp=dayByID(tempid);
		
		tmp.setIddays(iddays);
		tmp.setDate(date);
		tmp.setText(text);
		tmp.setLink(link);
		tmp.setCalendars_calendar_id(calendars_calendar_id);
		dayEJB.updateDay(tmp);
		return "admin.xhtml";
	}
	
	/**
	 * method called from the show calenadar xhtml redirects to editxhtml
	 * sets the bean
	 * @param dayId
	 * @return
	 */
	public String editDay(int dayId){
		tempid=dayId;
		Day tmp=new Day();
		tmp=dayByID(dayId);
		this.iddays=dayId;
		this.date=tmp.getDate();
		this.text=tmp.getText();
		this.link=tmp.getLink();
		this.calendars_calendar_id=tmp.getCalendars_calendar_id();
		
		return "editDay.xhtml";
	}
	
	public static String defaultDay(Date date, String text, String link, int calendars_calendar_id){
		Day tmp = new Day();
		
		tmp.setDate(date);
		tmp.setText(text);
		tmp.setLink(link);
		tmp.setCalendars_calendar_id(calendars_calendar_id);
		List<model.Calendar> temp =dayEJB.listAllCalendars();
		for (int i = 0; i < temp.size(); i++) {
			if(temp.get(i).getCalendarId()==calendars_calendar_id){
				dayEJB.createDay(tmp);
				return "true";
			}
		}
		System.out.println("No caledar with this ID "+calendars_calendar_id+" in database");
		FacesContext.getCurrentInstance().addMessage("myForm:calId", new FacesMessage("No caledar with this ID in database", "No caledar with this ID in database"));
		return"";
	}
}
