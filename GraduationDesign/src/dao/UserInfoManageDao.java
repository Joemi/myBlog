package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class UserInfoManageDao {

	public List<Map> getUserInfo(Integer id) {

		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Query query  = session.createSQLQuery("SELECT * FROM userinfo WHERE userId = ?");
		query.setParameter(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
			
		return list;
	}

	public void updateUserRealName(String realName, Integer userId,
			Session session) {
		Query query  = session.createSQLQuery("UPDATE user u SET u.realName = ? WHERE u.Id =  ?");
		query.setParameter(0, realName);
		query.setParameter(1, userId);
		int num = query.executeUpdate();
		
	}
}
