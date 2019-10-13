package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class IndexDao {

	public List<Map> getIndexListInfo(Integer num, Integer index, Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if(num == 1){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE  "
					+ "a.articleStatus = 1 AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(num == 2){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p, recommend r WHERE "
		+" a.articleStatus = 1 AND p.paramCode='type' AND a.type = p.paramValue AND a.Id = r.articleId ORDER BY r.Id DESC LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select i.*,p.paramText AS typeName FROM imformation i, paramlist p  WHERE "
		+" i.infoStatus = 1 AND p.paramCode='infoType' AND i.infoType = p.paramValue  ORDER BY i.Id DESC LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getIndexListCount(Integer num) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if(num == 1){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE  "
					+ "a.articleStatus = 1 AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(num == 2){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p, recommend r WHERE "
					+" a.articleStatus = 1 AND p.paramCode='type' AND a.type = p.paramValue AND a.Id = r.articleId ORDER BY r.Id DESC");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM imformation a, paramlist p WHERE  "
					+ "a.infoStatus = 1 AND p.paramCode='infoType' AND a.infoType = p.paramValue ORDER BY a.Id DESC");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list.size();
	}

}
