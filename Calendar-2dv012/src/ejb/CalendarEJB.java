package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Calendar;
import model.Day;
import model.UsersHasCalendar;
import model.UsersHasCalendarPK;

/**
 * Session Bean implementation class CalendarEJB
 */
@Stateless
@LocalBean
public class CalendarEJB {

	@PersistenceContext (name = "CalendarEJB")
	EntityManager em;

	public List<Calendar> listAllCalendars(){

		TypedQuery<Calendar> theQuery = em.createQuery("SELECT c FROM Calendar c", Calendar.class);
		List<Calendar> result = theQuery.getResultList();
		return result;  
	}

	public Calendar getCalendarById(int id){

		TypedQuery<Calendar> theQuery = em.createQuery("SELECT c FROM Calendar c", Calendar.class);
		List<Calendar> result = theQuery.getResultList();
		Calendar cal = new Calendar();

		for(int i=0;i<=result.size()-1;i++){
			if(result.get(i).getCalendarId()== id){

				cal = result.get(i);
			}	
		}	
		return cal;
	}

	public ArrayList<Calendar> listUsersCalendars(String username){

		ArrayList<Calendar> tmp = new ArrayList<Calendar>();
		TypedQuery<UsersHasCalendar> theQuery = em.createQuery("SELECT u FROM UsersHasCalendar u", UsersHasCalendar.class);
		List<UsersHasCalendar> result = theQuery.getResultList();
		ArrayList<UsersHasCalendarPK> pk = new ArrayList <UsersHasCalendarPK>();


		for(int i=0;i<result.size();i++){
			pk.add(result.get(i).getId()); 	
		}	

		for(int i = 0; i<pk.size();i++){
			if(pk.get(i).getUsersUsername().contentEquals(username)){

				tmp.add(getCalendarById(pk.get(i).getCalendarsCalendarId()));
			}	
		}
		return tmp;
	}


	public Calendar createCalendar(Calendar cl){

		em.persist(cl);
		em.flush();
		em.refresh(cl);
		System.out.println("Calendar added Successfully"); 
		return cl;



	}
	public void addUserHasCalendar(UsersHasCalendar cal){
		em.persist(cal);
		System.out.println("Calendar "+ cal.getId().getCalendarsCalendarId() +" assigned to user");
	}


	public UsersHasCalendar getCalendarsUser(int id){
		TypedQuery<UsersHasCalendar> theQuery = em.createQuery("SELECT u FROM UsersHasCalendar u ", UsersHasCalendar.class);
		List<UsersHasCalendar> result = theQuery.getResultList();

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).getId().getCalendarsCalendarId()==id)
				return result.get(i);
		}
		return null;
	}

	/**
	 * method to delete a user-calendar relation.
	 * @param id
	 */
	public void removeUserHasCalendar(int id){
		System.out.println("attempting to remove userhascalendar "+ id);
		TypedQuery<UsersHasCalendar> theQuery = em.createQuery("SELECT u FROM UsersHasCalendar u ", UsersHasCalendar.class);
		List<UsersHasCalendar> result = theQuery.getResultList();

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).getId().getCalendarsCalendarId()==id){
				em.remove(result.get(i));
				System.out.println("Calendar unassigned from user " + result.get(i).getId().getUsersUsername());
			}
		}

	}

	public void deleteCalendar(int calId){
		em.remove(getCalendarById(calId));
		System.out.println("Calendar removed: " + calId);
	}

	public void updateCalendar(Calendar cal){
		removeUserHasCalendar(cal.getCalendarId());

		Calendar tmp=em.find(Calendar.class, cal.getCalendarId());
		tmp.setBackground(cal.getBackground());
		tmp.setBegindate(cal.getBegindate());
		tmp.setCalendarname(cal.getCalendarname());
		tmp.setPublic_(cal.getPublic_());
		tmp.setCalendarId(cal.getCalendarId());
		this.em.flush();
		System.out.println("Calendar updated: " + cal.getCalendarId());
	}

	public void removeCalendarDays(int calenderId){

		TypedQuery<Day> theQuery = em.createQuery("SELECT d FROM Day d", Day.class);
		List<Day> result = theQuery.getResultList();

		for(int i=0;i<=result.size()-1;i++){
			if(result.get(i).getCalendars_calendar_id()==(calenderId)){
				em.remove(result.get(i));
				System.out.println("Day removed: " + result.get(i).getIddays());
			}	
		}	
	}
	
	public void createDay(Day d){
		 em.persist(d);
		 System.out.println("Day added Successfully"); 
	 }

}
