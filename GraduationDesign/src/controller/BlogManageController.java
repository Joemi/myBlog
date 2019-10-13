package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.BlogManageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.User;

@Controller
public class BlogManageController {
	
	Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	BlogManageService BMS = new BlogManageService();
	
	//获取博客信息
	@RequestMapping(value="/getBlogInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getBlogInfo(Integer id,String title, String type,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = BMS.getBlogInfo(id,title,type, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取博客数据失败\"}";
			}
	}
	
	//获取博客信息条数
	@RequestMapping(value="/getBlogCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getBlogCount(Integer id,String title, String type){
			try{
				Integer num = BMS.getBlogCount(id,title,type);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取博客数据失败\"}";
			}
	}
	
	//获取博客信息（本人登录）
	@RequestMapping(value="/getMyBlogInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyBlogInfo(Integer id, String type, String articleStatus,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = BMS.getMyBlogInfo(id,type,articleStatus, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取博客数据失败\"}";
			}
	}
	
	//获取博客信息条数（本人登录）
	@RequestMapping(value="/getMyBlogCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyBlogCount(Integer id, String type, String articleStatus){
			try{
				Integer num = BMS.getMyBlogCount(id,type,articleStatus);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取博客数据失败\"}";
			}
	}
	
	//删除博客信息
	@RequestMapping(value="/deleteBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteBlog(Integer id,Integer blogId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.deleteBlog(blogId);
				return "{\"code\":1,\"msg\":\"删除博客数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"删除博客数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//更新博客信息
	@RequestMapping(value="/editBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String editBlog(Integer id,Integer blogId,String condition,String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.editBlog(blogId,condition,picturePath);
				return "{\"code\":1,\"msg\":\"更新博客数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"更新博客数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//新增博客信息
	@RequestMapping(value="/addBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addBlog(Integer id,String condition,String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.addBlog(condition,user.getId(),picturePath);
				return "{\"code\":1,\"msg\":\"新增博客数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"新增博客数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//查看单条博客信息
	@RequestMapping(value="/checkBlogOne", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkBlogOne(Integer id,Integer blogId){
			try{
				List<Map> list = BMS.checkBlogOne(blogId);
				return gson.toJson(list.get(0));
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"查看该条博客数据失败\"}";
			}
	}
	
	//推荐博客成功
	@RequestMapping(value="/recommendBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String recommendBlog(Integer id,Integer blogId){
			try{
				String s = BMS.recommendBlog(id,blogId);
				return s;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"推荐博客失败\"}";
			}
	}
	
	//单条博客点击量增加
	@RequestMapping(value="/addBlogClick", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addBlogClick(Integer id,Integer blogId){
			try{
				String s = BMS.addBlogClick(blogId);
				return s;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"查看该条博客数据失败\"}";
			}
	}
}
