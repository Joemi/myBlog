package dao;

import java.util.List;

import resources.HibernateSessionFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.User;

public class LoginDao {

	public List<User> checkLoginWithoutPW(String userName){
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("from User u where u.userName = ?");
		query.setParameter(0, userName);
		List<User> list = query.list();
		
		t.commit();
		session.close();
		
		return list;
	}
	
	public List<User> checkLogin(String userName,String password){
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("select u from User u where u.userName = ? and u.password = ? and u.userType = 1");
		query.setParameter(0, userName);
		query.setParameter(1, password);
		List<User> list = query.list();
		
		t.commit();
		session.close();
		
		return list;
	}
}
