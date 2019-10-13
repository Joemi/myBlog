package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.LetterManageService;

import com.google.gson.Gson;

import entity.User;


@Controller
public class LetterManageController {

	Gson gson = new Gson();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	LetterManageService LMS = new LetterManageService();
	
	
	//获取邮件信息
	@RequestMapping(value="/getLetterInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLetterInfo(Integer id,Integer currentPage, Integer pageNumber, String type){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				List<Map> list = LMS.getLetterInfo(id, currentPage,pageNumber,Integer.parseInt(type));
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取邮件数据失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//获取邮件信息条数
	@RequestMapping(value="/getLetterCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLetterCount(Integer id, String type){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				Integer num = LMS.getLetterCount(id,Integer.parseInt(type));
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取邮件总条数失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//邮件一键操作:1一键已读，2一键删除
	@RequestMapping(value="/changeLetterStatus", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeLetterStatus(Integer id, String type, String eType){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				int num = LMS.changeLetterStatus(id,type,eType);
				if(num > 0){
					return "{\"code\":1,\"msg\":\"一键操作成功\"}";
				}
				else{
					return "{\"code\":2,\"msg\":\"没有可以操作的邮件\"}";
				}
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"一键操作失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//邮件删除
	@RequestMapping(value="/deleteLetter", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteLetter(Integer id, Integer letterId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.deleteLetter(id,letterId);
				return "{\"code\":1,\"msg\":\"删除成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"删除失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//邮件回复
	@RequestMapping(value="/replyLetter", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyLetter(Integer id, Integer visitorId, String content){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.replyLetter(id,visitorId,content,user.getRealName());
				return "{\"code\":1,\"msg\":\"邮件发送成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"邮件发送失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//使邮件已读
	@RequestMapping(value="/readMessage", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String readMessage(Integer id, Integer letterId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.readMessage(letterId);
				return "{\"code\":1,\"msg\":\"邮件已读成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"邮件已读失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
}
