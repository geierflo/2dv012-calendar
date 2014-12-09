package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users_has_calendars database table.
 * 
 */
@Entity
@Table(name="users_has_calendars")
@NamedQuery(name="UsersHasCalendar.findAll", query="SELECT u FROM UsersHasCalendar u")
public class UsersHasCalendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsersHasCalendarPK id;

	public UsersHasCalendar() {
	}

	public UsersHasCalendarPK getId() {
		return this.id;
	}

	public void setId(UsersHasCalendarPK id) {
		this.id = id;
	}

}