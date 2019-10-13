package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import service.LoginService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class LoginController {
	
	Gson gson = new Gson();
	LoginService LS = new LoginService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	//�û���¼
	@RequestMapping(value="/login", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String login(String userName,String password,HttpSession session){
		int num = LS.login(userName, password);
		if(num == 0){
			return "{\"code\":0,\"msg\":\"δע��\"}";
		}
		else if(num == 1){
			List<User> list = LS.loginInfo(userName, password);
			User user = list.get(0);
			ULS.setLogin(user);
			return "{\"code\":1,\"msg\":\"��¼�ɹ�\",\"UID\":\""+list.get(0).getId()+"\",\"UName\":\""+list.get(0).getRealName()+"\"}";
		}
		else if(num == 2){
			return "{\"code\":0,\"msg\":\"�û����������\"}";
		}
		else{
			return "{\"code\":0,\"msg\":\"�û�״̬�쳣\"}";
		}
	}
}
