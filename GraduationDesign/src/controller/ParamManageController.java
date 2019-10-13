package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ParamManageService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class ParamManageController {

	Gson gson = new Gson();
	
	ParamManageService PMS = new ParamManageService();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	//��ȡ�����Ϣ
	@RequestMapping(value="/getParamManageInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getParamManageInfo(Integer adminId,String paramName,Integer currentPage, Integer pageNumber){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				List<Map> list = PMS.getParamManageInfo(paramName, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ�������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//��ȡ�����Ϣ����
	@RequestMapping(value="/getParamManageCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getParamManageCount(Integer adminId,String paramName){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				Integer nums = PMS.getParamManageCount(paramName);
				return gson.toJson(nums);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ�����Ϣ����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//���������Ϣ
	@RequestMapping(value="/addParamList", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addParamList(Integer adminId,String condition){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				String res = PMS.addParamList(condition);
				return res;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//ɾ�������Ϣ
	@RequestMapping(value="/delParamList", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delParamList(Integer adminId,Integer paramId){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				String res = PMS.delParamList(paramId);
				return res;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ɾ��ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�༭�����Ϣ
	@RequestMapping(value="/editParamList", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String editParamList(Integer adminId,Integer paramId,String paramValue,String paramText){
		User user = ULS.getUser(adminId);
		if(user != null){
			try{
				String res = PMS.editParamList(paramId,paramValue,paramText);
				return res;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�༭ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
}
