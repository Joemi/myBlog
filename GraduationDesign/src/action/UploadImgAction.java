package action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadImgAction extends ActionSupport{

	
	private File file;
	private String fileContentType;
	private String fileFileName;
	String path;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public InputStream getSuccess() throws Exception{
		return new ByteArrayInputStream(path.getBytes("utf-8"));
	}
	
	public InputStream getError(){
		return null;
	}
	
	public void upload() throws Exception{
		//no file upload
		long time = System.nanoTime();
		String root = "F:\\DesignMe\\vue-element-scss\\src\\image\\"+time;
		InputStream is = new FileInputStream(file);

		OutputStream os = new FileOutputStream(new File(root, fileFileName));

		byte[] buffer = new byte[500];
		int length = 0;

		while (-1 != (length = is.read(buffer, 0, buffer.length))) {
			os.write(buffer);
		}
		System.out.println(root +"\\" + fileFileName);
		
		path = "{\"code\":\"1\", \"data\":\"..\\image\\"+time+"\\"+ fileFileName+"\"}";
		SendJSON.write(path);
		os.close();
		is.close();
	}
}
