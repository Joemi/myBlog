package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class SendJSON {
	public static void write(String obj) throws IOException{  
	    HttpServletResponse response=ServletActionContext.getResponse();    
	    response.setContentType("text/html;charset=utf-8");    
	    response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错 
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization"); 
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1. 
		response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");
	    PrintWriter out = response.getWriter();  
	    String jsonString= obj;  
	    out.println(jsonString);  
	    out.flush();  
	    out.close();  
	}  
}
