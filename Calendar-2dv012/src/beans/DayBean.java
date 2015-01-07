package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
	DayEJB dayEJB;
	
	private static final long serialVersionUID = 1L;
	
	private int iddays;
	private Date date;
	private String text;
	private String link;
	private int calendars_calendar_id;
	private CalendarBean calendarb;
	
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
		dayEJB.createDay(tmp);
		return "ListofDays.xhtml";
	}
	
	public List<Day> allDays(){
		return dayEJB.listAllDays();
		
	}
	
	public Day dayByID(){
		return dayEJB.getDayById(iddays);
	}
	
	public List<Day> calendarday(){
		return dayEJB.listCalendarDays(calendars_calendar_id);
		
	}

}
