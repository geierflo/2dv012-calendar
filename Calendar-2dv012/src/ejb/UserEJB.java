package ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.security.ISecurityManagement;

import model.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB {

    @PersistenceContext (name = "UserEJB")
    EntityManager em;
    
    public UserEJB() {
        // TODO Auto-generated constructor stub
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
				System.out.println("Data Added Successfully");  
				
				
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

    private String hash(String pw){
		
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
	        System.out.println(generatedPassword);


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
	
}
