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
	
	
	//��ȡ�ʼ���Ϣ
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
				return "{\"code\":0,\"msg\":\"��ȡ�ʼ�����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//��ȡ�ʼ���Ϣ����
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
				return "{\"code\":0,\"msg\":\"��ȡ�ʼ�������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�ʼ�һ������:1һ���Ѷ���2һ��ɾ��
	@RequestMapping(value="/changeLetterStatus", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeLetterStatus(Integer id, String type, String eType){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				int num = LMS.changeLetterStatus(id,type,eType);
				if(num > 0){
					return "{\"code\":1,\"msg\":\"һ�������ɹ�\"}";
				}
				else{
					return "{\"code\":2,\"msg\":\"û�п��Բ������ʼ�\"}";
				}
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"һ������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�ʼ�ɾ��
	@RequestMapping(value="/deleteLetter", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteLetter(Integer id, Integer letterId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.deleteLetter(id,letterId);
				return "{\"code\":1,\"msg\":\"ɾ���ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ɾ��ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�ʼ��ظ�
	@RequestMapping(value="/replyLetter", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replyLetter(Integer id, Integer visitorId, String content){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.replyLetter(id,visitorId,content,user.getRealName());
				return "{\"code\":1,\"msg\":\"�ʼ����ͳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�ʼ�����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//ʹ�ʼ��Ѷ�
	@RequestMapping(value="/readMessage", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String readMessage(Integer id, Integer letterId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				LMS.readMessage(letterId);
				return "{\"code\":1,\"msg\":\"�ʼ��Ѷ��ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�ʼ��Ѷ�ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
}
