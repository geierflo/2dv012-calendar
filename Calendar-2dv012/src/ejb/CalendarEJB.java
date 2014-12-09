package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Calendar;
import model.User;
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
			
			for(int i=0;i<=result.size()-1;i++){
	    		if(result.get(i).getCalendarId()== id){
	    			
	    			return result.get(i);
	    		}	
	    	}	
	    		return null;
			
			
		 }
	 
	 public ArrayList<Calendar> listUsersCalendars(String username){
	
		ArrayList<Calendar> tmp = new ArrayList<Calendar>();
		 TypedQuery<UsersHasCalendarPK> theQuery = em.createQuery("SELECT u FROM UsersHasCalendar u", UsersHasCalendarPK.class);
			List<UsersHasCalendarPK> result = theQuery.getResultList();
	    	
			for(int i=0;i<=result.size()-1;i++){
	    		if(result.get(i).getUsersUsername().contentEquals(username)){
	    		
	    			tmp.add(getCalendarById(result.get(i).getCalendarsCalendarId()));
	    		}	
	    	}	
	    		return tmp;
		}
	 
	 public void createCalendar(Calendar cl){
		 
		 em.persist(cl);
		 System.out.println("Data Added Successfully"); 
		 
	 }
		 
}
