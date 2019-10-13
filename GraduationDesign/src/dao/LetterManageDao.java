package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class LetterManageDao {

	public List<Map> getLetterInfo(Integer id, Integer index, Integer length,
			Integer type) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		
		if(type == 1){
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else if(type == 2){
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.letterStatus = '0' AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.letterStatus = '1' AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getLetterCount(Integer id, Integer type) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		List<Map> list = null;
		
		if(type == 1){
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC");
			query.setParameter(0, id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else if(type == 2){
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.letterStatus = '0' AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC");
			query.setParameter(0, id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select l.*,u.realName as name, p.paramText as statuName FROM letter l, user u, paramlist p"
		+" WHERE l.userId = ? AND l.letterStatus = '1' AND l.visitorId = u.Id AND p.paramValue = l.letterStatus AND p.paramCode = 'letterStatus' ORDER BY l.Id DESC");
			query.setParameter(0, id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		
		t.commit();
		session.close();
		return list.size();
	}
	
	public int updateLetterStatus(Integer id,String eType){
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		int num = 0;
		if(eType.equals("2")){
			Query query  = session.createSQLQuery("UPDATE letter l SET l.letterStatus = 1 WHERE l.userId = ?");
			query.setParameter(0, id);
			num = query.executeUpdate();
		}
		else{
			Query query  = session.createSQLQuery("UPDATE letter l SET l.letterStatus = 1 WHERE l.userId = ? AND l.letterStatus = ? ");
			query.setParameter(0, id);
			query.setParameter(1, eType);
			num = query.executeUpdate();
		}
		t.commit();
		session.close();
		return num;
	}

	public int deleteLetterStatus(Integer id,String eType){
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		int num = 0;
		if(eType.equals("2")){
			Query query  = session.createSQLQuery("DELETE FROM letter WHERE userId = ? ");
			query.setParameter(0, id);
			num = query.executeUpdate();
		}else{
			Query query  = session.createSQLQuery("DELETE FROM letter WHERE userId = ? AND letterStatus = ? ");
			query.setParameter(0, id);
			query.setParameter(1, eType);
			num = query.executeUpdate();
		}
		t.commit();
		session.close();
		return num;
	}
	
	public Integer getId(Session session){
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
