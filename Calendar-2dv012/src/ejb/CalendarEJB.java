package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Calendar;
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
		 System.out.println("Data Added Successfully"); 
		 return cl;
		 
		
		 
	 }
	 
	 public void addUserHasCalendar(UsersHasCalendar cal){
		 em.persist(cal);
		 System.out.println("Calendar assigned to user");
	 }
	 
	 
		 
}
