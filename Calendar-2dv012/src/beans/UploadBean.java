package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;

import ejb.CalendarEJB;


@javax.faces.bean.ManagedBean (name = "uploadBean")
@RequestScoped
public class UploadBean {

	private Part file;
	private CalendarEJB calEJB;
	private static String background;
	private Random rnd = new Random(10000);
	int count = 1;
	
	public Part getFile() {
		return file;
	}
	
	

	public static String getBackground() {
		return background;
	}



	public void setBackground(String background) {
		UploadBean.background = background;
	}



	public void setFile(Part file) {
		this.file = file;
	}
	
	public void upload() throws IOException{
		count =+1;
		String path = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("Data");
		
		try{
			InputStream in = file.getInputStream();
			byte[] data = new byte[in.available()];
			in.read(data);
			FileOutputStream out = new FileOutputStream(new File(path+"picture"+data.hashCode()+".jpeg"));
			setBackground("Datapicture"+data.hashCode()+".jpeg");
			System.out.println(background);
			
			out.write(data);
			
			in.close();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}

}
