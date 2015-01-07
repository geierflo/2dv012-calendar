package model;

import java.io.Serializable;
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
	
//	private int fk_days_calendars1;

	//bi-directional many-to-one association to Calendar
	@ManyToOne(fetch=FetchType.LAZY)
//	@MapsId("calendar_id")
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

//	public int getFk_days_calendars1() {
//		return fk_days_calendars1;
//	}
//
//	public void setFk_days_calendars1(int fk_days_calendars1) {
//		this.fk_days_calendars1 = fk_days_calendars1;
//	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}