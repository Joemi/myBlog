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

	//admin��¼
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String adminLogin(String userId, String password, HttpSession session) {
		int num = BS.login(userId, password);
		if (num == 0) {
			return "{\"code\":0,\"msg\":\"δע��\"}";
		} else if (num == 1) {
			List<User> list = BS.loginInfo(userId, password);
			User user = list.get(0);
			ULS.setLogin(user);
			return "{\"code\":1,\"msg\":\"��¼�ɹ�\",\"UID\":\""
					+ list.get(0).getId() + "\"}";
		} else if (num == 2) {
			return "{\"code\":0,\"msg\":\"���û���Ȩ��¼\"}";
		} else {
			return "{\"code\":0,\"msg\":\"�û�״̬�쳣\"}";
		}
	}
	
	//adminע��
	@RequestMapping(value = "/adminCancel", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String adminCancel(Integer adminId) {
		if(ULS.isLogin(adminId)){
			try{
				ULS.reomveUser(adminId);
				return "{\"code\":1,\"msg\":\"ע���ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ע��ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"ע��ʧ�ܣ��û�δ��¼\"}";
	}
}
