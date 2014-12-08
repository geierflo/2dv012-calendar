package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.User;
import ejb.UserEJB;

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
	
	public String addUser(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole("User");
		user.createUser(us);
		return "success.xhtml";
	}
	
	public String logIn(){
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		us.setRole("User");
		if(user.login(us)=="output"){
			return "loged-in";
		}else{
			return "loginfail";
		}
		
	}
}