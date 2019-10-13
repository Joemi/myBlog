package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;

public class UserManageDao {

	public List<Map> getUserManageInfo(String userName, Integer index,
			Integer length) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(userName)){
			Query query  = session.createSQLQuery("SELECT c.* ,p.paramText AS sexName FROM"+
"(SELECT ui.*,u.`status` AS `status`, p.paramText AS userStatus FROM `user` u ,userinfo ui,paramlist p WHERE u.Id = ui.userId "+
"AND p.paramCode='status' AND p.paramValue = u.`status`  ORDER BY u.Id DESC) c,paramlist p WHERE p.paramCode = 'sex' AND p.paramValue = c.sex LIMIT ?,?");
			query.setParameter(0, index);
			query.setParameter(1, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else{
			Query query  = session.createSQLQuery("SELECT c.* ,p.paramText AS sexName FROM"+
"(SELECT ui.*,u.`status` AS `status`, p.paramText AS userStatus FROM `user` u ,userinfo ui,paramlist p WHERE u.realName = ? AND u.Id = ui.userId "+
"AND p.paramCode='status' AND p.paramValue = u.`status`  ORDER BY u.Id DESC) c,paramlist p WHERE p.paramCode = 'sex' AND p.paramValue = c.sex LIMIT ?,?");
			query.setParameter(0, userName);
			query.setParameter(1, index);
			query.setParameter(2, length);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list;
	}

	public Integer getUserManageCont(String userName) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();

		List<Map> list = null;
		if("".equals(userName)){
			Query query  = session.createSQLQuery("SELECT ui.*,u.`status` AS `status` FROM `user` u ,userinfo ui WHERE  u.Id = ui.userId  ORDER BY u.Id DESC");
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		else{
			Query query  = session.createSQLQuery("SELECT ui.*,u.`status` AS `status` FROM `user` u ,userinfo ui WHERE u.realName = ? AND u.Id = ui.userId  ORDER BY u.Id DESC");
			query.setParameter(0, userName);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		}
		t.commit();
		session.close();
		return list.size();
	}

	public void changeUserStatus(Integer userId, Integer status) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		int num = 0;
		try{
			if(status == 0){
				Query query  = session.createSQLQuery("UPDATE user u SET u.`status` = 0 WHERE u.Id =  ?");
				query.setParameter(0, userId);
				num = query.executeUpdate();
			}
			else{
				Query query  = session.createSQLQuery("UPDATE user u SET u.`status` = 1 WHERE u.Id = ?");
				query.setParameter(0, userId);
				num = query.executeUpdate();
			}
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

}
