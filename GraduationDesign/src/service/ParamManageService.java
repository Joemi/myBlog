package service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import resources.HibernateSessionFactory;
import dao.ParamManageDao;
import entity.Paramlist;

public class ParamManageService {
	Gson gson = new Gson();
	ParamManageDao PMD = new ParamManageDao();

	public List<Map> getParamManageInfo(String paramName, Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		return PMD.getParamManageInfo(paramName,index,length);
	}

	public Integer getParamManageCount(String paramName) {
		return PMD.getParamManageCount(paramName);
	}

	public String addParamList(String condition) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Integer maxId = PMD.getId(session)+1;
		
		try{
			Paramlist param = new Paramlist();
			Paramlist paramlist = condition == null?param:gson.fromJson(condition,Paramlist.class);
			
			paramlist.setId(maxId);
			
			session.save(paramlist);
			
			t.commit();
			session.close();
			return "{\"code\":1,\"msg\":\"新增成功\"}";
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
			return "{\"code\":0,\"msg\":\"新增失败\"}";
		}
	}

	public String delParamList(Integer paramId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Paramlist paramlist = (Paramlist) session.get(Paramlist.class, paramId);
			
			session.delete(paramlist);
			
			t.commit();
			session.close();
			return "{\"code\":1,\"msg\":\"删除成功\"}";
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
			return "{\"code\":0,\"msg\":\"删除失败\"}";
		}
	}

	public String editParamList(Integer paramId, String paramValue, String paramText) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Paramlist paramlist = (Paramlist) session.get(Paramlist.class, paramId);
			
			paramlist.setParamValue(paramValue);
			paramlist.setParamText(paramText);
			
			session.update(paramlist);
			
			t.commit();
			session.close();
			return "{\"code\":1,\"msg\":\"编辑成功\"}";
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
			return "{\"code\":0,\"msg\":\"编辑失败\"}";
		}
	}

}
