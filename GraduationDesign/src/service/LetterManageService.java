package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.LetterManageDao;
import entity.Letter;

public class LetterManageService {

	LetterManageDao LMD = new LetterManageDao();
	
	public List<Map> getLetterInfo(Integer id, Integer currentPage,
			Integer pageNumber, Integer type) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		return LMD.getLetterInfo(id,index, length, type);
	}

	public Integer getLetterCount(Integer id, Integer type) {
		return LMD.getLetterCount(id,type);
	}

	public int changeLetterStatus(Integer id, String type,String eType) {
		int num = 0;
		if(type.equals("1")){
			num = LMD.updateLetterStatus(id,eType);
		}
		else{
			num = LMD.deleteLetterStatus(id,eType);
		}
		
		return num;
		
	}

	public void deleteLetter(Integer id, Integer letterId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			Letter letter = (Letter) session.get(Letter.class, letterId);
			
			session.delete(letter);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

	public void replyLetter(Integer id, Integer visitorId, String content,
			String realName) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			Integer maxId = LMD.getId(session) +1;
			
			Letter lt = new Letter();
			lt.setId(maxId);
			lt.setUserId(visitorId);
			lt.setVisitorId(id);
			lt.setContent(content);
			lt.setCreateDate(format1.format(new Date()));
			lt.setLetterStatus("0");
			lt.setTitle("来自用户 "+realName+" 的留言回复");
			
			session.save(lt);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

	public void readMessage(Integer letterId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			Letter lt = (Letter) session.get(Letter.class, letterId);
			lt.setLetterStatus("1");
			
			session.update(lt);
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

}
