package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class RecommendManageDao {

	public List<Map> getRecBlogInfo(String title, String type, Integer index,
			Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.articleStatus = 0 ORDER BY a.Id DESC LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(!"".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type'  AND a.type = p.paramValue AND a.title= ? AND a.articleStatus = 0 ORDER BY a.Id DESC LIMIT ?,?");
			query.setParameter(0, title);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else if("".equals(title) && !"".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.type= ? AND a.articleStatus = 0 ORDER BY a.Id DESC LIMIT ?,?");
			query.setParameter(0, type);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.title= ? AND a.type= ? AND a.articleStatus = 0 ORDER BY a.Id DESC LIMIT ?,?");
			query.setParameter(0, title);
			query.setParameter(1, type);
			query.setParameter(2, index);
			query.setParameter(3, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getRecBlogCount(String title, String type) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.articleStatus = 0 ORDER BY a.Id DESC");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(!"".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type'  AND a.type = p.paramValue AND a.title= ? AND a.articleStatus = 0 ORDER BY a.Id DESC");
			query.setParameter(0, title);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else if("".equals(title) && !"".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.type= ? AND a.articleStatus = 0 ORDER BY a.Id DESC");
			query.setParameter(0, type);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE "
					+" p.paramCode='type' AND a.type = p.paramValue AND a.title= ? AND a.type= ? AND a.articleStatus = 0 ORDER BY a.Id DESC");
			query.setParameter(0, title);
			query.setParameter(1, type);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list.size();
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
