package dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import resources.HibernateSessionFactory;


public class AlbumDao {

	//��ȡ��������ID
	public Integer getId(Session session){
		Query query  = session.createSQLQuery("select p.id as id from picture p ORDER BY p.id DESC");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		
		if(list.size() == 0){
			return 0;
		}
		else{
			return Integer.parseInt(list.get(0).get("id").toString());
		}
	}

	//��ȡͼƬ�б�
	public List<Map> getPictureList(Integer id) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Query query  = session.createSQLQuery("SELECT id,`describes`,picturePath FROM picture WHERE userId = ?");
		query.setParameter(0, id);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		t.commit();
		session.close();
			
		return list;
	}



}
