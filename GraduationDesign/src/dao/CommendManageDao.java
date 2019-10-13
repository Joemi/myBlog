package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class CommendManageDao {

	public List<Map> getCommendInfo(Integer articleId, Integer index,
			Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		Query query  = session.createSQLQuery("SELECT c.*,u.realName as name FROM `comment` c, `user` u WHERE c.articleId = ? AND c.userId = u.Id ORDER BY c.Id DESC LIMIT ?,?");
		query.setParameter(0, articleId);
		query.setParameter(1, index);
		query.setParameter(2, length);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		t.commit();
		session.close();
		return list;
	}

	public Integer getCommendCount(Integer articleId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		Query query  = session.createSQLQuery("SELECT c.* FROM `comment` c WHERE c.articleId = ?  ORDER BY c.Id DESC");
		query.setParameter(0, articleId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		t.commit();
		session.close();
		return list.size();
	}

	public int getId(Session session) {
		Query query  = session.createSQLQuery("SELECT * FROM `comment` ORDER BY Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("id").toString());
		}
	}
	
	public int getIdS(Session session) {
		Query query  = session.createSQLQuery("SELECT * FROM letter ORDER BY Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("id").toString());
		}
	}

	public int getClickRate(Integer articleId, Session session) {
		Query query  = session.createSQLQuery("SELECT * FROM article where Id = ? ORDER BY Id DESC");
		query.setParameter(0, articleId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("commentNum").toString());
		}
	}

}
