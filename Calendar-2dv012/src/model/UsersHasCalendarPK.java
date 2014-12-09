package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the users_has_calendars database table.
 * 
 */
@Embeddable
public class UsersHasCalendarPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="users_username", insertable=false, updatable=false)
	private String usersUsername;

	@Column(name="calendars_calendar_id", insertable=false, updatable=false)
	private int calendarsCalendarId;

	public UsersHasCalendarPK() {
	}
	public String getUsersUsername() {
		return this.usersUsername;
	}
	public void setUsersUsername(String usersUsername) {
		this.usersUsername = usersUsername;
	}
	public int getCalendarsCalendarId() {
		return this.calendarsCalendarId;
	}
	public void setCalendarsCalendarId(int calendarsCalendarId) {
		this.calendarsCalendarId = calendarsCalendarId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsersHasCalendarPK)) {
			return false;
		}
		UsersHasCalendarPK castOther = (UsersHasCalendarPK)other;
		return 
			this.usersUsername.equals(castOther.usersUsername)
			&& (this.calendarsCalendarId == castOther.calendarsCalendarId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usersUsername.hashCode();
		hash = hash * prime + this.calendarsCalendarId;
		
		return hash;
	}
}