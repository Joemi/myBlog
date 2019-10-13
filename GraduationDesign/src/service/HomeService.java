package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.HomeDao;
import entity.User;

public class HomeService {

	HomeDao HD = new HomeDao();


	public String changePw(String oldPassword, String newPassword, Integer id) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		List<Map> list = HD.checkOldPW(oldPassword, id);
		if(list.size() > 0){
			try{
				User user = (User) session.get(User.class, id);
				user.setPassword(newPassword);
				user.setCreateDate(format1.format(new Date()));
				
				session.update(user);
				
				t.commit();
				session.close();
				
				return "{\"code\":1,\"msg\":\"修改成功\"}";
				
			}catch(Exception e){
				e.printStackTrace();
				t.rollback();
				session.close();
				return "{\"code\":0,\"msg\":\"修改失败\"}";
			}
		}else{
			return "{\"code\":2,\"msg\":\"原密码错误，请重新输入\"}";
		}
		
	}


	public Integer mailInfo(Integer id) {
		return HD.mailInfo(id);
	}
}
