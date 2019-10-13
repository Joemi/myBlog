package service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import resources.HibernateSessionFactory;
import dao.BlogManageDao;
import entity.Article;
import entity.Recommend;

public class BlogManageService {
	
	BlogManageDao BMD = new BlogManageDao();
	
	Gson gson = new Gson();

	public List<Map> getBlogInfo(Integer id, String title, String type,Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		List<Map> list = BMD.getBlogInfo(id,title,type,index,length);
		return list;
	}

	public Integer getBlogCount(Integer id,String title, String type) {
		
		return BMD.getBlogCount(id,title,type);
	}

	public void deleteBlog(Integer blogId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			Article artic = (Article) session.get(Article.class, blogId);
			
			session.delete(artic);
			
			BMD.deleteCommentByArticleId(artic.getId(),session);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

	public void editBlog(Integer blogId, String condition, String picturePath) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		try{
			Article a = new Article();
			
			Article article = condition==null?a:gson.fromJson(condition,Article.class);
			
			article.setId(blogId);
			article.setPicturePath(picturePath);
			article.setCreateDate(format1.format(new Date()));
			
			if("2".equals(article.getArticleStatus())){				//如果博客审核未通过，修改之后可重新审核
				article.setArticleStatus("0");
			}
			
			session.update(article);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
		
	}

	public void addBlog(String condition,Integer id, String picturePath) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Integer maxId = BMD.getMaxId(session) + 1;
		
		try{
			Article a = new Article();
			
			Article article = condition==null?a:gson.fromJson(condition,Article.class);
			
			article.setId(maxId);
			article.setUserId(id);
			article.setArticleStatus("0");
			article.setClickRate(Long.parseLong("0"));
			article.setCommentNum(0);
			article.setPicturePath(picturePath);
			article.setCreateDate(format1.format(new Date()));
			
			session.save(article);
			
			t.commit();
			session.close();
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
		}
	}

	public List<Map> checkBlogOne(Integer blogId) {
		return BMD.checkBlogOne(blogId);
	}

	public String recommendBlog(Integer id, Integer blogId) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			List<Map> b = BMD.recommendBlog(id,blogId,session);
			
			if(b.size() == 0){
				Recommend r = new Recommend();
				r.setId(1);
				r.setArticleId(blogId);
				r.setRecommendRate((long) 1);
				r.setUserId(id);
				r.setCreateDate(format1.format(new Date()));
				
				session.save(r);
			}
			else{
				Integer rId = Integer.parseInt(b.get(0).get("id").toString());
				Long recommendRates = (long) (Integer.parseInt(b.get(0).get("recommendRate").toString()) +1);
				Recommend re = (Recommend) session.get(Recommend.class, rId);
				
				re.setRecommendRate(recommendRates);
				
				session.update(re);
				
			}
			
			t.commit();
			session.close();
			return "{\"code\":1,\"msg\":\"推荐博客成功\"}";
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
			return "{\"code\":0,\"msg\":\"推荐博客失败\"}";
		}
	}

	public String addBlogClick(Integer blogId) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		try{
			
			Article a = (Article) session.get(Article.class, blogId);
			
			Long clickRates = (long) (BMD.getClickRate(blogId, session)+1);
			
			a.setClickRate(clickRates);
			
			session.update(a);
			
			t.commit();
			session.close();
			return "{\"code\":1,\"msg\":\"点击量增加成功\"}";
			
		}catch(Exception e){
			e.printStackTrace();
			t.rollback();
			session.close();
			return "{\"code\":0,\"msg\":\"点击量增加失败\"}";
		}
	}

	//获取博客信息（本人登录）
	public List<Map> getMyBlogInfo(Integer id,  String type,
			String articleStatus, Integer currentPage, Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		List<Map> list = BMD.getMyBlogInfo(id,type,articleStatus,index,length);
		return list;
	}

	//获取博客信息条数（本人登录）
	public Integer getMyBlogCount(Integer id, String type,String articleStatus) {
		return BMD.getMyBlogCount(id,type,articleStatus);
	}

}
