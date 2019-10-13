package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import dao.UserInfoManageDao;
import resources.HibernateSessionFactory;
import entity.Userinfo;

public class UserInfoManageService {
	
	Gson gson = new Gson();
	
	UserInfoManageDao UMD = new UserInfoManageDao();

	public List<Map> getUserInfo(Integer id) {

		return UMD.getUserInfo(id);
	}

	public void updateUserInfo(String condition, Integer uId, String picturePath) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			Userinfo u = new Userinfo();
			Userinfo userInfo = condition==null?u:gson.fromJson(condition,Userinfo.class);
			userInfo.setId(uId);
			userInfo.setHeadPortrait(picturePath);
			userInfo.setCreateDate(format1.format(new Date()));
			
			session.update(userInfo);
			
			UMD.updateUserRealName(userInfo.getRealName(), userInfo.getUserId(),session);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

}
