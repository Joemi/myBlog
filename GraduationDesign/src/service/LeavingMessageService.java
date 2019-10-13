package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.LeavingMessageDao;
import entity.Letter;
import entity.Messageboard;

public class LeavingMessageService {
	
	LeavingMessageDao LMD = new LeavingMessageDao();

	public List<Map> getLeavingInfo(Integer id, Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		List<Map> list = LMD.getLeavingInfo(id, index, length);
		return list;
	}

	public Integer getLeavingCount(Integer id) {
		return LMD.getLeavingCount(id);
	}

	public void deleteLeavingInfo(Integer leavingId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Messageboard mb = (Messageboard) session.get(Messageboard.class, leavingId);
			
			session.delete(mb);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

	public void addLeavingInfo(Integer id, String content, Integer checkId,String realName) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Integer maxId = LMD.getId() + 1;
		
		Integer maxIds = LMD.getIds() +1;
		
		try{
			Messageboard mb = new Messageboard();
			mb.setId(maxId);
			mb.setContent(content);
			mb.setMessageStatus("0");
			mb.setUserId(id);
			mb.setCreateDate(format1.format(new Date()));
			
			Letter letter = new Letter();
			letter.setId(maxIds);
			letter.setUserId(checkId);
			letter.setContent(content);
			letter.setLetterStatus("0");
			letter.setVisitorId(id);
			letter.setCreateDate(format1.format(new Date()));
			letter.setTitle("来自用户 "+realName+" 的留言");
			
			session.save(mb);
			session.save(letter);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

	public void replyleaving(Integer vositorId, String name,
			String content, Integer id) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Integer maxIds = LMD.getIds() +1;
		
		try{
		
			Letter lt = new Letter();
			
			lt.setId(maxIds);
			lt.setLetterStatus("0");
			lt.setCreateDate(format1.format(new Date()));
			lt.setContent(content);
			lt.setTitle("来自用户 "+ name +" 的留言回复");
			lt.setVisitorId(id);
			lt.setUserId(vositorId);
			
			session.save(lt);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

}
