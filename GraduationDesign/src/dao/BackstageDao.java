package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import entity.User;

public class BackstageDao {

	public List<User> checkLogin(String userId, String password) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("select u from User u where u.userName = ? and u.password = ? and u.userType = 0");
		query.setParameter(0, userId);
		query.setParameter(1, password);
		List<User> list = query.list();
		
		t.commit();
		session.close();
		
		return list;
	}

	public List<User> checkLoginWithoutPW(String userId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("from User u where u.userName = ?");
		query.setParameter(0, userId);
		List<User> list = query.list();
		
		t.commit();
		session.close();
		
		return list;
	}

}
