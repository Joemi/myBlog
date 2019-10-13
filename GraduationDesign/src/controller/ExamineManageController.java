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
	
	//��ȡ��˲�����Ϣ
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
				return "{\"code\":0,\"msg\":\"��ȡ��˲�������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//��ȡ��˲�����Ϣ����
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
				return "{\"code\":0,\"msg\":\"��ȡ��˲�������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	
	//��˲�����Ϣ
	@RequestMapping(value="/examineBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String examineBlog(Integer adminId,Integer blogId,Integer userId, String status){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				RMS.examineBlog(adminId,blogId,userId,status);
				return "{\"code\":1,\"msg\":\"��˳ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"���ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
		}
	
}
