package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.User;
import ejb.UserEJB;

/**
 * @author Flo
 *
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserEJB user;

	private String username;
	private String password;
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getAllUsers() {
		return user.getListofUsers();

	}
	/**
	 * method used for the user self registration
	 * @return
	 */
	public String addUser(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole("User");
		if(user.createUser(us))
			return "success.xhtml";

		return "unsuccess.xhtml";
	}

	/**
	 * method used for the admin user creation
	 * @return
	 */
	public String createUser(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole(role);
		if(user.createUser(us))
			return "admin.xhtml";

		return "unsuccess.xhtml";
	}

	public String logIn(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole(user.findUserByName(username).getRole());
		if(user.login(us)=="output"){
			if (us.getRole().equals("Admin"))		
				return "admin.xhtml";
			else 
				return "success.xhtml";
		}else{
			return "unsuccess.xhtml";
		}

	}

	public void logout() {  
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();  
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");  
		user=null;
		username=null;
		password=null;
		role=null;
	}  
	
	/**
	 * method to delete a user, its users and days
	 * @param calId
	 * @return
	 */
	public String deleteUser(String username){
		System.out.println("deleting user "+ username);
		user.removeUserHasCalendar(username);
		user.deleteUser(username);
		return "admin.xhtml";
	}
	
	public String editUser(String username){
		System.out.println("editing user "+ username);
		User temp= user.findUserByName(username);
		this.username=temp.getUsername();
		this.password=temp.getPassword();
		this.role=temp.getRole();
		return "editUser.xhtml";
	}
	
	public String updateUser(){
		System.out.println("updating user "+ username);
		
		User tmp= user.findUserByName(username);
		tmp.setUsername(username);
		tmp.setPassword(user.hash(password));
		tmp.setRole(role);

		user.updateUser(tmp);
		return "home.xhtml";
	}
	public String password(){
		System.out.println("updating password for "+ username);
		User temp= user.findUserByName(username);
		this.username=temp.getUsername();
		this.password=temp.getPassword();
		this.role=temp.getRole();

		return "password.xhtml";
	}
	
	
}