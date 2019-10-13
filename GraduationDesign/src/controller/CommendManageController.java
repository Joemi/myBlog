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
	
	//��ȡ������Ϣ
	@RequestMapping(value="/getCommendInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCommendInfo(Integer id,Integer articleId,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = CMS.getCommendInfo(articleId, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//��ȡ������Ϣ����
	@RequestMapping(value="/getCommendCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCommendCount(Integer id,Integer articleId){
			try{
				Integer num = CMS.getCommendCount(articleId);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//ɾ��������Ϣ
	@RequestMapping(value="/deleteComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteComment(Integer id,Integer commentId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.deleteComment(commentId);
				return "{\"code\":1,\"msg\":\"ɾ���������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ɾ����������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�ظ�������Ϣ
	@RequestMapping(value="/replyComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyComment(Integer id, Integer vositorId, String content,String articTitle){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.replyComment(vositorId,articTitle,content,user.getId());
				return "{\"code\":1,\"msg\":\"�ظ��������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�ظ���������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//����������Ϣ �����е�¼���˶�����������
	@RequestMapping(value="/addComment", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addComment(Integer id, Integer articleId,Integer vositorId, String content,String articTitle){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				CMS.addComment(user.getId(),articleId,vositorId,content,articTitle);
				return "{\"code\":1,\"msg\":\"�����������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"������������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
}
