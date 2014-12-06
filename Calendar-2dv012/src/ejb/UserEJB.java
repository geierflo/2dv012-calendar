package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public List<User> getListofUsers() {
    	TypedQuery<User> theQuery = em.createQuery("SELECT u FROM User u", User.class);
    	List<User> result = theQuery.getResultList();
    	return result;
    }

    
}
