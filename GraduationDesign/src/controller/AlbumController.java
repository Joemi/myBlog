package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import service.AlbumService;
import entity.User;


@Controller
public class AlbumController {
	
	Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	AlbumService AS = new AlbumService();

	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	//添加相册图片
	@RequestMapping(value="/addPicture", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addNewPicture(Integer id, String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				AS.addNewPicture(id,picturePath);
				return "{\"code\":1,\"msg\":\"添加相册图片成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"添加相册图片失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//删除相册图片
	@RequestMapping(value = "/deletePicture", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deletePicture(Integer id, Integer pictureId, String picturePath) {
		User user = ULS.getUser(id);
		if (user != null) {
			try {
				AS.deletePicture(pictureId,picturePath);
				return "{\"code\":1,\"msg\":\"添加相册图片成功\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"添加相册图片失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//更新图片描述
	@RequestMapping(value = "/updateDescribe", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateDescribe(Integer id, Integer pictureId, String describe) {
		User user = ULS.getUser(id);
		if (user != null) {
			try {
				AS.updateDescribe(pictureId,describe);
				return "{\"code\":1,\"msg\":\"更新图片描述成功\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"更新图片描述失败\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"您还未登录，请登录后再操作\"}";
	}
	
	//获取图片列表
	@RequestMapping(value = "/getPictureList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPictureList(Integer id) {
		try {
			List<Map> list = AS.getPictureList(id);
			return gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"code\":0,\"msg\":\"获取图片列表失败\"}";
		}
	}
	
	
}
