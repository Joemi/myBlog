package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserManageService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class UserManageController {
	
	Gson gson = new Gson();
	
	UserManageService UMS = new UserManageService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	//获取用户信息信息
	@RequestMapping(value="/getUserManageInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserManageInfo(Integer adminId,String userName,Integer currentPage, Integer pageNumber){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				List<Map> list = UMS.getUserManageInfo(userName, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取用户信息数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//获取用户信息条数
	@RequestMapping(value="/getUserManageCont", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserManageCont(Integer adminId,String userName){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				Integer nums = UMS.getUserManageCont(userName);
				return gson.toJson(nums);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取用户信息数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//恢复禁用权限处理
	@RequestMapping(value="/changeUserStatus", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeUserStatus(Integer adminId,Integer userId, Integer status){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				UMS.changeUserStatus(userId,status);
				return "{\"code\":1,\"msg\":\"恢复禁用权限处理数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"恢复禁用权限处理数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
		}


}
