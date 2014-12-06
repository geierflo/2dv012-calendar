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
	
	public List<User> getAllUsers() {
		return user.getListofUsers();
	
	}
	
}