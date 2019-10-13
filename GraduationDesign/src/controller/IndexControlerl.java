package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.IndexService;

import com.google.gson.Gson;

@Controller
public class IndexControlerl {
	Gson gson = new Gson();
	
	IndexService IS = new IndexService();
	
	//��ȡ��ҳչʾ������Ϣ
	@RequestMapping(value="/getIndexListInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getIndexListInfo(Integer num,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = IS.getIndexListInfo(num, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��ҳչʾ������Ϣ����ʧ��\"}";
			}
	}
	
	//��ȡ��ҳչʾ������Ϣ����
	@RequestMapping(value="/getIndexListCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getIndexListCount(Integer num){
			try{
				Integer nums = IS.getIndexListCount(num);
				return gson.toJson(nums);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��ҳչʾ������Ϣ����ʧ��\"}";
			}
	}
}
