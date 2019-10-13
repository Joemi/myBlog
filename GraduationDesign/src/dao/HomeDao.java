package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class HomeDao {
	
	public List<Map> checkOldPW(String oldPW, Integer id){
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("select u.* from user u where u.`password` = ? AND u.Id = ?");
		query.setParameter(0, oldPW);
		query.setParameter(1, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		
		t.commit();
		session.close();
		return list;
		
	}

	public Integer mailInfo(Integer id) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("SELECT * FROM letter WHERE userId = ? AND letterStatus = 0");
		query.setParameter(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		
		t.commit();
		session.close();
		return list.size();
	}
}
