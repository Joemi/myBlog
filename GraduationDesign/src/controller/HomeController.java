package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import entity.User;
import service.HomeService;

@Controller
public class HomeController {
	
	HomeService HS = new HomeService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	Gson gson = new Gson();

	//�޸�����
	@RequestMapping(value="/changePW", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String changePW(Integer id, String oldPassword,String newPassword,HttpSession session){
		User user = ULS.getUser(id);
		if(user != null) {
			try{
				String res = HS.changePw(oldPassword, newPassword, user.getId());
				return res;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�޸�����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//ע���û�
	@RequestMapping(value="/cancel", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String cancel(Integer id){
		if(ULS.isLogin(id)){
			try{
				ULS.reomveUser(id);
				return "{\"code\":1,\"msg\":\"ע���ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ע��ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"ע��ʧ�ܣ��û�δ��¼\"}";
	}
	
	//��ȡδ����Ϣ����
	@RequestMapping(value="/mailInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String mailInfo(Integer id){
		User user = ULS.getUser(id);
		if(user != null) {
			try{
				Integer num = HS.mailInfo(id);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡδ����Ϣ����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
}
