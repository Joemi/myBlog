package service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import resources.HibernateSessionFactory;
import dao.AlbumDao;
import entity.Picture;

public class AlbumService {

	AlbumDao AD = new AlbumDao();

	//添加相册图片
	public void addNewPicture(Integer id, String picturePath) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Picture picture = new Picture();
		picture.setId(AD.getId(session)+1);
		picture.setUserId(id);
		picture.setCreateDate(format1.format(new Date()));
		picture.setPicturePath(picturePath);
		//picture.setDescribe(describe);
		
		session.save(picture);
		
		t.commit();
		session.close();
	}

	//删除相册图片
	public void deletePicture(Integer pictureId, String picturePath) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Picture p = (Picture) session.get(Picture.class, pictureId);
		
		session.delete(p);
		
		String[] paths = picturePath.split("=");
		String path = paths[paths.length - 1];
		
		// 删除服务器上的原图片！！！！！！！！！！！！！！！！！！！！！
		File f = new File(path);
		f.delete(); // 删除图片

		String[] s = path.split("/");
		int i = 0;
		int total = s.length - 1;
		String newString = "";
		for (i = 0; i < total; i++) {
			if (i < (total - 1)) {
				newString = newString + s[i] + "/";
			} else {
				newString = newString + s[i];
			}
		}

		File d = new File(newString);
		d.delete(); // 删除文件夹
		
		
		t.commit();
		session.close();
		
		
	}

	//更新图片描述
	public void updateDescribe(Integer pictureId, String describe) {
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Picture p = (Picture) session.get(Picture.class, pictureId);
		p.setDescribes(describe);
		p.setCreateDate(format1.format(new Date()));
		
		session.update(p);
		
		t.commit();
		session.close();
	}

	//获取图片列表
	public List<Map> getPictureList(Integer id) {
		return AD.getPictureList(id);
	}
}
