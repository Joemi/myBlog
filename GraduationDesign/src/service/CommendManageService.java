package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.CommendManageDao;
import entity.Article;
import entity.Comment;
import entity.Letter;

public class CommendManageService {

	CommendManageDao CMD = new CommendManageDao();
	
	public List<Map> getCommendInfo(Integer articleId, Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		List<Map> list = CMD.getCommendInfo(articleId,index,length);
		return list;
	}

	public Integer getCommendCount(Integer articleId) {
		return CMD.getCommendCount(articleId);
	}

	public void deleteComment(Integer commentId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			Comment comment = (Comment) session.get(Comment.class, commentId);
			
			
			
			Integer commentNumS = CMD.getClickRate(comment.getArticleId(),session)-1;//获取评论数量
			
			Article a = (Article) session.get(Article.class, comment.getArticleId());
			a.setCommentNum(commentNumS);
			
			session.delete(comment);
			session.update(a);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

	public void replyComment(Integer vositorId, String articTitle,
			String content, Integer id) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			
			Integer maxId = CMD.getIdS(session) +1;	//信件
			
			Letter lt = new Letter();
			
			lt.setId(maxId);
			lt.setLetterStatus("0");
			lt.setCreateDate(format1.format(new Date()));
			lt.setContent(content);
			lt.setTitle("来自文章 "+ articTitle +" 的评论回复");
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

	public void addComment(Integer id, Integer articleId,Integer vositorId, String content, String articTitle) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Integer maxId = CMD.getId(session) + 1;	//评论id
		
		Integer maxIds = CMD.getIdS(session) +1;//信件id
		
		Integer commentNumS = CMD.getClickRate(articleId,session)+1;//获取评论数量
		try{
			
			Letter lt = new Letter();
			
			lt.setId(maxIds);
			lt.setLetterStatus("0");
			lt.setCreateDate(format1.format(new Date()));
			lt.setContent(content);
			lt.setTitle("来自文章 "+ articTitle +" 的评论");
			lt.setVisitorId(id);
			lt.setUserId(vositorId);
			
			Comment comment = new Comment();
			comment.setId(maxId);
			comment.setArticleId(articleId);
			comment.setContent(content);
			comment.setCreateDate(format1.format(new Date()));
			comment.setCommentStatus("0");
			comment.setUserId(id);
			
			Article a = (Article) session.get(Article.class, articleId);
			a.setCommentNum(commentNumS);
			
			session.save(lt);
			session.save(comment);
			session.update(a);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

}
