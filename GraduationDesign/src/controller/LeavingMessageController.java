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

	//��ȡ���԰���Ϣ
	@RequestMapping(value="/getLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLeavingInfo(Integer id,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = LMS.getLeavingInfo(id, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//��ȡ���԰���Ϣ����
	@RequestMapping(value="/getLeavingCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getLeavingCount(Integer id){
			try{
				Integer num = LMS.getLeavingCount(id);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ����������ʧ��\"}";
			}
	}
	
	//ɾ��������Ϣ
	@RequestMapping(value="/deleteLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteLeavingInfo(Integer id,Integer leavingId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.deleteLeavingInfo(leavingId);
				return "{\"code\":1,\"msg\":\"ɾ�����Գɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ɾ������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//����������Ϣ
	@RequestMapping(value="/addLeavingInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addLeavingInfo(Integer id,String content,Integer checkId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.addLeavingInfo(id,content,checkId,user.getRealName());
				return "{\"code\":1,\"msg\":\"�������Գɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�ظ�������Ϣ
	@RequestMapping(value="/replyleaving", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyleaving(Integer id, Integer vositorId, String content){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.replyleaving(vositorId,user.getRealName(),content,user.getId());
				return "{\"code\":1,\"msg\":\"�ظ��������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�ظ���������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
}
