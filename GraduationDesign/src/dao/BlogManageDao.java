package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class BlogManageDao {

	public List<Map> getBlogInfo(Integer id,String title, String type, Integer index, Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.userId = ? "
					+ "AND a.articleStatus = 1 AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(!"".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.userId = ? "
					+ "AND a.articleStatus = 1 AND a.title= ? AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, title);
			query.setParameter(2, index);
			query.setParameter(3, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else if("".equals(title) && !"".equals(type)){
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.userId = ? "
					+ "AND a.articleStatus = 1 AND a.type= ? AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, type);
			query.setParameter(2, index);
			query.setParameter(3, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}else{
			Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.userId = ? "
					+ "AND a.articleStatus = 1 AND a.title= ? AND a.type= ? AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
			query.setParameter(0, id);
			query.setParameter(1, title);
			query.setParameter(2, type);
			query.setParameter(3, index);
			query.setParameter(4, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getBlogCount(Integer id,String title, String type) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		List<Map> list = null;
		if("".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select * FROM article WHERE userId = ? AND articleStatus = 1 ORDER BY Id DESC");
			query.setParameter(0, id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if(!"".equals(title) && "".equals(type)){
			Query query  = session.createSQLQuery("select * FROM article WHERE userId = ? AND title= ? AND articleStatus = 1 ORDER BY Id DESC");
			query.setParameter(0, id);
			query.setParameter(1, title);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else if("".equals(title) && !"".equals(type)){
			Query query  = session.createSQLQuery("select * FROM article WHERE userId = ? AND type= ? AND articleStatus = 1 ORDER BY Id DESC");
			query.setParameter(0, id);
			query.setParameter(1, type);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else{
			Query query  = session.createSQLQuery("select * FROM article WHERE userId = ? AND title = ? AND type= ? AND articleStatus = 1 ORDER BY Id DESC");
			query.setParameter(0, id);
			query.setParameter(1, title);
			query.setParameter(2, type);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list.size();
	}

	public Integer getMaxId(Session session) {
		Query query  = session.createSQLQuery("select u.id as id from article u ORDER BY u.Id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("id").toString());
		}
	}

	public List<Map> checkBlogOne(Integer blogId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.Id = ? AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC");
		query.setParameter(0, blogId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
		return list;
	}

	public List<Map> recommendBlog(Integer id, Integer blogId, Session session) {
		Query query  = session.createSQLQuery("SELECT * FROM recommend WHERE userId = ? AND articleId = ?");
		query.setParameter(0, id);
		query.setParameter(1, blogId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		return list;
	}

	public int getClickRate(Integer blogId, Session session) {
		Query query  = session.createSQLQuery("SELECT * FROM article where Id = ? ORDER BY Id DESC");
		query.setParameter(0, blogId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("clickRate").toString());
		}
	}

	public void deleteCommentByArticleId(Integer id, Session session) {
		Query query  = session.createSQLQuery("DELETE FROM `comment` WHERE  articleId = ? ");
		query.setParameter(0, id);
		int num = query.executeUpdate();
	}

	//获取博客信息（本人登录）
	public List<Map> getMyBlogInfo(Integer id, String type, String articleStatus,
			Integer index, Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		
		String querySql = querySql(type,articleStatus);
		
		Query query  = session.createSQLQuery("select a.*,p.paramText AS typeName FROM article a, paramlist p WHERE a.userId = ? "
				+ querySql+ " AND p.paramCode='type' AND a.type = p.paramValue ORDER BY Id DESC LIMIT ?,?");
		query.setParameter(0, id);
		query.setParameter(1, index);
		query.setParameter(2, length);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		t.commit();
		session.close();
		return list;
	}

	//获取博客信息条数（本人登录）
	public Integer getMyBlogCount(Integer id, String type, String articleStatus) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		List<Map> list = null;
		String querySql = querySql(type,articleStatus);
		
		Query query  = session.createSQLQuery("select * FROM article a WHERE a.userId = ? "+querySql+" ORDER BY Id DESC");
		query.setParameter(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		
		t.commit();
		session.close();
		return list.size();
	}
	
	//拼接查询sql字符串
	public String querySql(String type,String articleStatus){
		String sql = "";
		if(!"".equals(type)){
			sql += "AND a.type = " + type + " ";
		}
		if(!"".equals(articleStatus)){
			sql += "AND a.articleStatus = " + articleStatus + "";
		}
		return sql;
	}
}
