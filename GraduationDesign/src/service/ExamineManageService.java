package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.RecommendManageDao;
import entity.Article;
import entity.Letter;

public class ExamineManageService {

	RecommendManageDao RMD = new RecommendManageDao();
	
	public List<Map> getExamineBlogInfo(String title, String type,
			Integer currentPage, Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		return RMD.getRecBlogInfo(title,type,index, length);
	}

	public Integer getExamineBlogCount(String title, String type) {
		return RMD.getRecBlogCount(title,type);
	}

	public void examineBlog(Integer adminId, Integer blogId, Integer userId, String status) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Article article = (Article) session.get(Article.class, blogId);

			article.setArticleStatus(status);
			
			Letter letter = new Letter();
			
			letter.setId(RMD.getId(session) +1);
			letter.setUserId(userId);
			letter.setVisitorId(adminId);
			letter.setCreateDate(format1.format(new Date()));
			letter.setLetterStatus("0");
			letter.setTitle("���Թ���Ա�Ĳ��������Ϣ");
			
			if("2".equals(status)){
				letter.setContent("�𾴵��û�����������'"+article.getTitle()+"'δͨ����ˣ������Ŭ��������Ϣ��أ���");
			}else{
				letter.setContent("�𾴵��û�����������'"+article.getTitle()+"'ͨ����ˣ����ٽ�����������Ϣ��أ���");
			}
			
			session.save(letter);
			session.update(article);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

}
