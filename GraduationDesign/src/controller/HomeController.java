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

	//修改密码
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
				return "{\"code\":0,\"msg\":\"修改密码失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//注销用户
	@RequestMapping(value="/cancel", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String cancel(Integer id){
		if(ULS.isLogin(id)){
			try{
				ULS.reomveUser(id);
				return "{\"code\":1,\"msg\":\"注销成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"注销失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"注销失败，用户未登录\"}";
	}
	
	//获取未读消息数据
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
				return "{\"code\":0,\"msg\":\"获取未读消息数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
}
