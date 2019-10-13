package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import entity.User;
import entity.Userinfo;
import resources.HibernateSessionFactory;

public class RegisterDao {

	
	public void register(String userName, String password){
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			String realName = "”√ªß" + userName;
			User user = new User();
			Integer id = getId(session)+1;
			user.setId(id);
			user.setRealName(realName);
			user.setUserName(userName);
			user.setPassword(password);
			user.setUserType("1");
			user.setStatus("0");
			user.setCreateDate(format1.format(new Date()));
			
			Userinfo uf = new Userinfo();
			uf.setId(getIds(session)+1);
			uf.setUserId(id);
			uf.setRealName(realName);
			uf.setSex("0");
			uf.setCreateDate(format1.format(new Date()));
			
			session.save(user);
			session.save(uf);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}
	
	
	public Integer getId(Session session){
		Query query  = session.createSQLQuery("select u.Id as Id from user u ORDER BY u.Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("Id").toString());
		}
	}
	
	public Integer getIds(Session session){
		Query query  = session.createSQLQuery("select u.Id as Id from userInfo u ORDER BY u.Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("Id").toString());
		}
	}
}
