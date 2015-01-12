package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
	private User loggedInUser;
	private String url;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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
		if(user.createUser(us)){
			loggedInUser=us;
			return "success.xhtml";
		}


		FacesContext.getCurrentInstance().addMessage("registerForm2:uname", new FacesMessage("Username already taken", "Username already taken"));
		return "";
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

		FacesContext.getCurrentInstance().addMessage("registerForm:fname", new FacesMessage("Username already taken", "Username already taken"));
		return "";
	}

	public String logIn(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole("User");

		if(user.login(us)=="output"){
			us.setRole(user.findUserByName(username).getRole());
			loggedInUser=us;

			if (us.getRole().equals("Admin"))		
				return "admin.xhtml";
			else 
				return "success.xhtml";
		}else{
			FacesContext.getCurrentInstance().addMessage("loginForm:bottom", new FacesMessage("Wrong Username or password", "Wrong Username or password"));
			return "";
		}

	}

	/**
	 * method that checks if a user is logged in 
	 * redirects the user from the home.xhtml to either admin or success and prevents anonymous user and user to visit pages without permission
	 * @return
	 */
	public String loggedIn(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String urli=origRequest.getRequestURL().toString();
		urli=urli.substring(urli.lastIndexOf("/") + 1);

		if(urli.contains("bye.xhtml"))
			return"";

		//redirection mechanisms for logged in users
		if(urli.contains("home.xhtml")){
			if (loggedInUser!=null){
				if(loggedInUser.getRole().contentEquals("Admin"))
					return "admin.xhtml";
				else
					return "success.xhtml";
			}
			else 
				return"";
		}

		//security check for address manipulation
		else if(loggedInUser==null&&(!urli.contains("bye")&&!urli.contains("register")&&!urli.contains("home")&&!urli.contains("public")&&!urli.contains("news")&&!urli.contains("contact")&&!urli.contains("about")))
			return"noaccess.xhtml";
		else if ((loggedInUser!=null&&loggedInUser.getRole().contentEquals("User"))&&(urli.contains("admin")||urli.contains("creat")||urli.contains("edit")))
			return"noaccess.xhtml";
		else return"";


	}

	public String logout() {  
		username=null;
		password=null;
		role=null;
		loggedInUser=null;

		return"bye.xhtml";
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