package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class ParamManageDao {

	public List<Map> getParamManageInfo(String paramName, Integer index,
			Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(paramName)){
			Query query  = session.createSQLQuery("SELECT * FROM paramlist ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else{
			Query query  = session.createSQLQuery("SELECT * FROM paramlist WHERE paramName = ? ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, paramName);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getParamManageCount(String paramName) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(paramName)){
			Query query  = session.createSQLQuery("SELECT * FROM paramlist ORDER BY Id");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else{
			Query query  = session.createSQLQuery("SELECT * FROM paramlist WHERE paramName = ? ORDER BY Id");
			query.setParameter(0, paramName);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list.size();
	}

	public int getId(Session session) {
		Query query  = session.createSQLQuery("select u.Id as Id from paramlist u ORDER BY u.Id DESC");
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
