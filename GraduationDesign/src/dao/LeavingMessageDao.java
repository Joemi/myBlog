package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class LeavingMessageDao {

	public List<Map> getLeavingInfo(Integer id, Integer index,
			Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("select m.*,u.realName as name FROM messageboard m, user u WHERE m.userId = ? AND m.userId = u.Id ORDER BY m.Id DESC LIMIT ?,?");
		query.setParameter(0, id);
		query.setParameter(1, index);
		query.setParameter(2, length);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
		return list;
	}

	public Integer getLeavingCount(Integer id) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("select * FROM messageboard WHERE userId = ? ORDER BY Id DESC");
		query.setParameter(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
		return list.size();
	}
	
	public Integer getId(){
		Session session = HibernateSessionFactory.getSession();
		Query query  = session.createSQLQuery("select u.Id as Id from messageboard u ORDER BY u.Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("Id").toString());
		}
	}
	
	public Integer getIds(){
		Session session = HibernateSessionFactory.getSession();
		Query query  = session.createSQLQuery("select u.Id as Id from letter u ORDER BY u.Id DESC");
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
