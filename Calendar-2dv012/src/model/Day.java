package model;

import java.io.Serializable;
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

	private String date;

	private String link;

	private String text;

	//bi-directional many-to-one association to Calendar
	@ManyToOne
	@JoinColumn(name="calendars_calendar_id")
	private Calendar calendar;

	public Day() {
	}

	public int getIddays() {
		return this.iddays;
	}

	public void setIddays(int iddays) {
		this.iddays = iddays;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}