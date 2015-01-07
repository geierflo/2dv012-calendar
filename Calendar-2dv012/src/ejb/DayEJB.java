package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Day;

/**
 * Session Bean implementation class DayEJB
 */
@Stateless
@LocalBean
public class DayEJB {

	 @PersistenceContext (name = "DayEJB")
	    EntityManager em;
	 
	 public List<Day> listAllDays(){
		 
		TypedQuery<Day> theQuery = em.createQuery("SELECT d FROM Day d", Day.class);
		List<Day> result = theQuery.getResultList();
		return result;  
	 }
	 
	 public Day getDayById(int id){
		 
			TypedQuery<Day> theQuery = em.createQuery("SELECT d FROM Day d", Day.class);
			List<Day> result = theQuery.getResultList();
			
			for(int i=0;i<=result.size()-1;i++){
	    		if(result.get(i).getIddays()== id){
	    			
	    			return result.get(i);
	    		}	
	    	}	
	    		return null;
		 }
	 
	 public ArrayList<Day> listCalendarDays(int calenderId){
	
		ArrayList<Day> tmp = new ArrayList<Day>();
		 TypedQuery<Day> theQuery = em.createQuery("SELECT d FROM Day d", Day.class);
			List<Day> result = theQuery.getResultList();
	    	
			for(int i=0;i<=result.size()-1;i++){
	    		if(result.get(i).getCalendars_calendar_id()==(calenderId)){
	    			tmp.add(result.get(i));
	    		}	
	    	}	
	    		return tmp;
		}
	 
	 public void createDay(Day d){
		 
		 em.persist(d);
		 System.out.println("Data Added Successfully"); 
		 
	 }
		 
}
