package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the calendars database table.
 * 
 */
@Entity
@Table(name="calendars")
@NamedQuery(name="Calendar.findAll", query="SELECT c FROM Calendar c")

public class Calendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="calendar_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int calendarId;
	
	private String background;

	@Temporal(TemporalType.DATE)
	private Date begindate;

	private String calendarname;

	@Column(name="public")
	private byte public_;

	public Calendar() {
	}

	public int getCalendarId() {
		return this.calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getBackground() {
		return this.background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Date getBegindate() {
		return this.begindate;
	}

	public void setBegindate(Date date) {
		this.begindate = date;
	}

	public String getCalendarname() {
		return this.calendarname;
	}

	public void setCalendarname(String calendarname) {
		this.calendarname = calendarname;
	}

	public byte getPublic_() {
		return this.public_;
	}

	public void setPublic_(byte public_) {
		this.public_ = public_;
	}

}