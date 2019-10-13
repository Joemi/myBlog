package action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

	public String login(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String userName = request.getParameter("uaerName");
		String password = request.getParameter("password");
		if("wdd".equals(userName) && "123456".equals(password)){
			return "{code:1,msg:'ok'}";
		}
		return "111";
	}
}
