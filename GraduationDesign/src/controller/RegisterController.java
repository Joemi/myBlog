package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.RegisterService;

@Controller
public class RegisterController {

	RegisterService RS = new RegisterService();
	
	//�û�ע��
	@RequestMapping(value="/register", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public @ResponseBody String register(String userName,String password){
		try{
			RS.registerUser(userName, password);
			return "{\"code\":1,\"msg\":\"ע��ɹ�\"}";
		}catch(Exception e){
			e.printStackTrace();
			return "{\"code\":0,\"msg\":\"ע��ʧ��\"}";
		}
	}
}
