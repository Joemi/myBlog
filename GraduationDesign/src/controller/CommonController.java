package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CommonService;

import com.google.gson.Gson;

import entity.User;

@Controller
public class CommonController {

	Gson gson = new Gson();
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	CommonService CS = new CommonService();
	
	//公共获取下拉菜单的方法
	@RequestMapping(value="/getParamListInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getParamListInfo(Integer id,String paramCode){
			try{
				List<Map> list = CS.getParamListInfo(paramCode);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"获取码表数据失败\"}";
			}
	}
}
