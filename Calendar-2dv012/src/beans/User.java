package beans;  


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  





import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;  
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;  
import javax.naming.Context;  
import javax.naming.InitialContext;  
import javax.naming.NamingException;  
import javax.sql.DataSource;  

@ManagedBean(name = "user")  
@SessionScoped  
public class User {  

	private String userName;  
	private String password;
	private String dbPassword;  
	private String dbName;  
	DataSource ds;  

	public User() {  
		try {  
			Context ctx = new InitialContext();  
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");  
		} catch (NamingException e) {  
			e.printStackTrace();  
		}  
	}  

	public String getDbPassword() {  
		return dbPassword;  
	}  

	public String getDbName() {  
		return dbName;  
	}  

	public String getuserName() {  
		return userName;  
	}  

	public void setuserName(String name) {  
		this.userName = name;  
	}  

	public String getPassword() {  
		return password;  
	}  

	public void setPassword(String password) {  
		this.password = password;  
	}  

	public String add() {  
		int i = 0;  
		if (userName != null) {  
			PreparedStatement ps = null;  
			Connection con = null;  
			try {  
				if (ds != null) {  
					con = ds.getConnection();  
					if (con != null) {  
						String sql = "INSERT INTO users(username, password) VALUES(?,?)";  
						ps = con.prepareStatement(sql);  
						ps.setString(1, userName);  
						ps.setString(2, hash(password));  
						i = ps.executeUpdate();  
						System.out.println("Data Added Successfully");  
					}  
				}  
			} catch (Exception e) {  
				System.out.println(e);  
			} finally {  
				try {  
					con.close();  
					ps.close();  
				} catch (Exception e) {  
					e.printStackTrace();  
				}  
			}  
		}  
		if (i > 0) {  
			return "success";  
		} else  
			return "unsuccess";  
	}  

	public void dbData(String uName) {  
		if (uName != null) {  
			PreparedStatement ps = null;  
			Connection con = null;  
			ResultSet rs = null;  

			if (ds != null) {  
				try {  
					con = ds.getConnection();  
					if (con != null) {  
						String sql = "select username,password from users where username = '"  
								+ uName + "'";  
						ps = con.prepareStatement(sql);  
						rs = ps.executeQuery();  
						rs.next();  
						dbName = rs.getString("userName");  
						dbPassword = rs.getString("password");  
					}  
				} catch (SQLException sqle) {  
					sqle.printStackTrace();  
				}  
			}  
		}  
	}  

	public String login() {
		dbData(userName);
		if (userName.equals(dbName) && hash(password).equals(dbPassword)) {
				return "output";  
			    }
		return "invalid";
	}  

	public void logout() {  
		FacesContext.getCurrentInstance().getExternalContext()  
		.invalidateSession();  
		FacesContext.getCurrentInstance()  
		.getApplication().getNavigationHandler()  
		.handleNavigation(FacesContext.getCurrentInstance(), null, "/home.xhtml");  
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
}