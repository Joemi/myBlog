package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ExamineManageService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class ExamineManageController {

	Gson gson = new Gson();
	
	ExamineManageService RMS = new ExamineManageService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	//获取审核博客信息
	@RequestMapping(value="/getExamineBlogInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getExamineBlogInfo(Integer adminId,String title, String type,Integer currentPage, Integer pageNumber){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				List<Map> list = RMS.getExamineBlogInfo(title,type, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取审核博客数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//获取审核博客信息条数
	@RequestMapping(value="/getExamineBlogCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getExamineBlogCount(Integer adminId,String title, String type){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				Integer num = RMS.getExamineBlogCount(title,type);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取审核博客数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	
	//审核博客信息
	@RequestMapping(value="/examineBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String examineBlog(Integer adminId,Integer blogId,Integer userId, String status){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				RMS.examineBlog(adminId,blogId,userId,status);
				return "{\"code\":1,\"msg\":\"审核成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"审核失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
		}
	
}
