package ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Calendar;
import model.Day;
import model.User;
import model.UsersHasCalendar;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB {

	@PersistenceContext (name = "UserEJB")
	EntityManager em;

	public UserEJB() {
	}

	public boolean createUser(User user){
		boolean created = false;
		User tmp = findUserByName(user.getUsername());

		if (user.getUsername() != null || tmp.getUsername() != user.getUsername()) {    
			try {  

				String hashPass = hash(user.getPassword());
				user.setPassword(hashPass); 
				em.persist(user);
				created = true;
				System.out.println("Data Added Successfully."+ user.getRole());  


			} catch (Exception e) {
				System.out.println(e);  

			}  
		}
		return created;
	}

	public List<User> getListofUsers() {
		TypedQuery<User> theQuery = em.createQuery("SELECT u FROM User u", User.class);
		List<User> result = theQuery.getResultList();
		return result;
	}

	public String hash(String pw){

		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(pw.getBytes());
			//Get the hash's bytes
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;

			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public String login(User user) {
		String resultTag = "invalid";
		User tmp= findUserByName(user.getUsername());
		if(tmp == null){
			return "invalid";
		}
		String hashed = hash(user.getPassword());
		if(tmp.getUsername().contentEquals(user.getUsername()) &&
				tmp.getPassword().contentEquals(hashed)){
			resultTag= "output";

		}else
			resultTag= "invalid";

		return resultTag;
	}



	public User findUserByName(String username){


		TypedQuery<User> theQuery = em.createQuery("SELECT u FROM User u", User.class);
		List<User> result = theQuery.getResultList();

		for(int i=0;i<=result.size()-1;i++){
			if(result.get(i).getUsername().contentEquals(username)){

				return result.get(i);
			}	
		}	
		return null;
	}

	/**
	 * method to delete a user-calendar relation.
	 * @param id
	 */
	public void removeUserHasCalendar(String username){
		System.out.println("attempting to remove userhascalendar for "+ username);
		TypedQuery<UsersHasCalendar> theQuery = em.createQuery("SELECT u FROM UsersHasCalendar u ", UsersHasCalendar.class);
		List<UsersHasCalendar> result = theQuery.getResultList();

		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getId().getUsersUsername()+" is the same idiot as "+username);
			if (result.get(i).getId().getUsersUsername().equals(username)){
				em.remove(result.get(i));
				System.out.println("Calendar "+ result.get(i).getId().getCalendarsCalendarId() +" unassigned from user " + result.get(i).getId().getUsersUsername());
				removeCalendarDays(result.get(i).getId().getCalendarsCalendarId());
				em.remove(getCalendarById(result.get(i).getId().getCalendarsCalendarId()));
				System.out.println("Calendar "+result.get(i).getId().getCalendarsCalendarId()+" deleted");
			}
		}

	}

	public void deleteUser(String username){
		em.remove(findUserByName(username));
		System.out.println("User removed: " + username);
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

	public void updateUser(User newUser){
		User tmp= em.find(User.class, newUser.getUsername());

		tmp.setPassword(newUser.getPassword());
		tmp.setRole(newUser.getRole());
		tmp.setUsername(newUser.getUsername());

		this.em.flush();
		System.out.println("User updated: " + tmp.getUsername());
	}

}
