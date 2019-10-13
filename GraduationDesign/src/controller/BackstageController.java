package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.BackstageService;
import entity.User;

@Controller
public class BackstageController {
	
	BackstageService BS = new BackstageService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();

	//admin登录
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String adminLogin(String userId, String password, HttpSession session) {
		int num = BS.login(userId, password);
		if (num == 0) {
			return "{\"code\":0,\"msg\":\"未注册\"}";
		} else if (num == 1) {
			List<User> list = BS.loginInfo(userId, password);
			User user = list.get(0);
			ULS.setLogin(user);
			return "{\"code\":1,\"msg\":\"登录成功\",\"UID\":\""
					+ list.get(0).getId() + "\"}";
		} else if (num == 2) {
			return "{\"code\":0,\"msg\":\"该用户无权登录\"}";
		} else {
			return "{\"code\":0,\"msg\":\"用户状态异常\"}";
		}
	}
	
	//admin注销
	@RequestMapping(value = "/adminCancel", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String adminCancel(Integer adminId) {
		if(ULS.isLogin(adminId)){
			try{
				ULS.reomveUser(adminId);
				return "{\"code\":1,\"msg\":\"注销成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"注销失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"注销失败，用户未登录\"}";
	}
}
