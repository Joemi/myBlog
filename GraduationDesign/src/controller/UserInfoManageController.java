package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserInfoManageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.User;
import entity.Userinfo;

@Controller
public class UserInfoManageController {

	Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	UserInfoManageService UMS = new UserInfoManageService();
	
	//获取用户信息
	@RequestMapping(value="/getUserInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(Integer id){
			try{
				List<Map> userInfo = UMS.getUserInfo(id);
				return gson.toJson(userInfo.get(0));
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取用户数据失败\"}";
			}
	}
	
	//更新用户信息
	@RequestMapping(value="/updateUserInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserInfo(Integer id,Integer uId, String condition,String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				UMS.updateUserInfo(condition,uId,picturePath);
				return "{\"code\":1,\"msg\":\"更新用户数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"更新用户数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
}
