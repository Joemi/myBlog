package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.BlogManageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.User;

@Controller
public class BlogManageController {
	
	Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	UserLoginSet ULS = UserLoginSet.getuserLogin();
	
	BlogManageService BMS = new BlogManageService();
	
	//��ȡ������Ϣ
	@RequestMapping(value="/getBlogInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getBlogInfo(Integer id,String title, String type,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = BMS.getBlogInfo(id,title,type, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//��ȡ������Ϣ����
	@RequestMapping(value="/getBlogCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getBlogCount(Integer id,String title, String type){
			try{
				Integer num = BMS.getBlogCount(id,title,type);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//��ȡ������Ϣ�����˵�¼��
	@RequestMapping(value="/getMyBlogInfo", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyBlogInfo(Integer id, String type, String articleStatus,Integer currentPage, Integer pageNumber){
			try{
				List<Map> list = BMS.getMyBlogInfo(id,type,articleStatus, currentPage,pageNumber);
				return gson.toJson(list);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//��ȡ������Ϣ���������˵�¼��
	@RequestMapping(value="/getMyBlogCount", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyBlogCount(Integer id, String type, String articleStatus){
			try{
				Integer num = BMS.getMyBlogCount(id,type,articleStatus);
				return gson.toJson(num);
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"��ȡ��������ʧ��\"}";
			}
	}
	
	//ɾ��������Ϣ
	@RequestMapping(value="/deleteBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteBlog(Integer id,Integer blogId){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.deleteBlog(blogId);
				return "{\"code\":1,\"msg\":\"ɾ���������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"ɾ����������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//���²�����Ϣ
	@RequestMapping(value="/editBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String editBlog(Integer id,Integer blogId,String condition,String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.editBlog(blogId,condition,picturePath);
				return "{\"code\":1,\"msg\":\"���²������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"���²�������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//����������Ϣ
	@RequestMapping(value="/addBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addBlog(Integer id,String condition,String picturePath){
		User user = ULS.getUser(id);
		if(user != null){
			try{
				BMS.addBlog(condition,user.getId(),picturePath);
				return "{\"code\":1,\"msg\":\"�����������ݳɹ�\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"������������ʧ��\"}";
			}
		}
		return "{\"code\":0,\"msg\":\"����δ��¼�����¼���ٲ���\"}";
	}
	
	//�鿴����������Ϣ
	@RequestMapping(value="/checkBlogOne", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkBlogOne(Integer id,Integer blogId){
			try{
				List<Map> list = BMS.checkBlogOne(blogId);
				return gson.toJson(list.get(0));
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�鿴������������ʧ��\"}";
			}
	}
	
	//�Ƽ����ͳɹ�
	@RequestMapping(value="/recommendBlog", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String recommendBlog(Integer id,Integer blogId){
			try{
				String s = BMS.recommendBlog(id,blogId);
				return s;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�Ƽ�����ʧ��\"}";
			}
	}
	
	//�������͵��������
	@RequestMapping(value="/addBlogClick", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addBlogClick(Integer id,Integer blogId){
			try{
				String s = BMS.addBlogClick(blogId);
				return s;
			}catch(Exception e){
				e.printStackTrace();
				return "{\"code\":0,\"msg\":\"�鿴������������ʧ��\"}";
			}
	}
}
