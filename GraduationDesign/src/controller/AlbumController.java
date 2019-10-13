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
	
	//������ͼƬ
	@RequestMapping(value="/addPicture", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addNewPicture(Integer id, String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				AS.addNewPicture(id,picturePath);
				return "{\"code\":1,\"msg\":\"������ͼƬ�ɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"������ͼƬʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//ɾ�����ͼƬ
	@RequestMapping(value = "/deletePicture", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deletePicture(Integer id, Integer pictureId, String picturePath) {
		User user = ULS.getUser(id);
		if (user != null) {
			try {
				AS.deletePicture(pictureId,picturePath);
				return "{\"code\":1,\"msg\":\"������ͼƬ�ɹ�\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"������ͼƬʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//����ͼƬ����
	@RequestMapping(value = "/updateDescribe", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateDescribe(Integer id, Integer pictureId, String describe) {
		User user = ULS.getUser(id);
		if (user != null) {
			try {
				AS.updateDescribe(pictureId,describe);
				return "{\"code\":1,\"msg\":\"����ͼƬ�����ɹ�\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"����ͼƬ����ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//��ȡͼƬ�б�
	@RequestMapping(value = "/getPictureList", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getPictureList(Integer id) {
		try {
			List<Map> list = AS.getPictureList(id);
			return gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"code\":0,\"msg\":\"��ȡͼƬ�б�ʧ��\"}";
		}
	}
	
	
}
