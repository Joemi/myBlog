package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import service.LeavingMessageService;
import entity.User;

@Controller
public class LeavingMessageController {
	
	LeavingMessageService LMS = new LeavingMessageService();
	Gson gson = new Gson();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();

	//获取留言板信息
	@RequestMapping(value="/getLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLeavingInfo(Integer id,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = LMS.getLeavingInfo(id, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取留言数据失败\"}";
			}
	}
	
	//获取留言板信息条数
	@RequestMapping(value="/getLeavingCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLeavingCount(Integer id){
			try{
				Integer num = LMS.getLeavingCount(id);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取留言总条数失败\"}";
			}
	}
	
	//删除留言信息
	@RequestMapping(value="/deleteLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteLeavingInfo(Integer id,Integer leavingId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.deleteLeavingInfo(leavingId);
				return "{\"code\":1,\"msg\":\"删除留言成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"删除留言失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//新增留言信息
	@RequestMapping(value="/addLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addLeavingInfo(Integer id,String content,Integer checkId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.addLeavingInfo(id,content,checkId,user.getRealName());
				return "{\"code\":1,\"msg\":\"发送留言成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"发送留言失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//回复留言信息
	@RequestMapping(value="/replyleaving", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyleaving(Integer id, Integer vositorId, String content){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.replyleaving(vositorId,user.getRealName(),content,user.getId());
				return "{\"code\":1,\"msg\":\"回复留言数据成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"回复留言数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
}
