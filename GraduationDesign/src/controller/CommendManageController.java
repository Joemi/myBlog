package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CommendManageService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class CommendManageController {

	Gson gson = new Gson();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	CommendManageService CMS = new CommendManageService();
	
	//获取评论信息
	@RequestMapping(value="/getCommendInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCommendInfo(Integer id,Integer articleId,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = CMS.getCommendInfo(articleId, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取评论数据失败\"}";
			}
	}
	
	//获取评论信息数量
	@RequestMapping(value="/getCommendCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCommendCount(Integer id,Integer articleId){
			try{
				Integer num = CMS.getCommendCount(articleId);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取评论数据失败\"}";
			}
	}
	
	//删除评论信息
	@RequestMapping(value="/deleteComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteComment(Integer id,Integer commentId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.deleteComment(commentId);
				return "{\"code\":1,\"msg\":\"删除评论数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"删除评论数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//回复评论信息
	@RequestMapping(value="/replyComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyComment(Integer id, Integer vositorId, String content,String articTitle){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.replyComment(vositorId,articTitle,content,user.getId());
				return "{\"code\":1,\"msg\":\"回复评论数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"回复评论数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//新增评论信息 （所有登录的人都可以新增）
	@RequestMapping(value="/addComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addComment(Integer id, Integer articleId,Integer vositorId, String content,String articTitle){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.addComment(user.getId(),articleId,vositorId,content,articTitle);
				return "{\"code\":1,\"msg\":\"新增评论数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"新增评论数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
}
