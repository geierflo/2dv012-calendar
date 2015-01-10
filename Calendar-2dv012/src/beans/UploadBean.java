package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;



@javax.faces.bean.ManagedBean (name = "uploadBean")
@RequestScoped
public class UploadBean {

	private Part file;
	private static String background;
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
			System.out.println("saving path is: "+path);
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
