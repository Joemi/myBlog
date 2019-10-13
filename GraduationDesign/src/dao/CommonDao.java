package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class CommonDao {

	public List<Map> getParamListInfo(String paramCode) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("SELECT paramValue AS value, paramText AS label FROM paramlist WHERE paramCode = ?");
		query.setParameter(0, paramCode);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
		return list;
	}

}
