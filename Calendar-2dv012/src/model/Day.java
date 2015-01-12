package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the days database table.
 * 
 */
@Entity
@Table(name="days")
@NamedQuery(name="Day.findAll", query="SELECT d FROM Day d")
public class Day implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int iddays;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String link;
	
	private String text;
	
	private int calendars_calendar_id;
	

	//bi-directional many-to-one association to Calendar
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="calendars_calendar_id", insertable = false, updatable = false)
	private Calendar calendar;

	public int getCalendars_calendar_id() {
		return calendars_calendar_id;
	}

	public void setCalendars_calendar_id(int calendars_calendar_id) {
		this.calendars_calendar_id = calendars_calendar_id;
	}

	public Day() {
	}

	public int getIddays() {
		return this.iddays;
	}

	public void setIddays(int iddays) {
		this.iddays = iddays;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public String getLink() {
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		   Date today = new Date();
		   System.out.println("Today is the "+dateFormat.format(today));
		if(date.after(today)){
			this.link="lockedCalendar.xhtml";
			this.text="iframe";
		}		
		
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}