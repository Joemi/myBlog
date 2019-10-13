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

	//������ͼƬ
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

	//ɾ�����ͼƬ
	public void deletePicture(Integer pictureId, String picturePath) {
		Session session = HibernateSessionFactory.getSession();
		Transaction t = session.beginTransaction();
		
		Picture p = (Picture) session.get(Picture.class, pictureId);
		
		session.delete(p);
		
		String[] paths = picturePath.split("=");
		String path = paths[paths.length - 1];
		
		// ɾ���������ϵ�ԭͼƬ������������������������������������������
		File f = new File(path);
		f.delete(); // ɾ��ͼƬ

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
		d.delete(); // ɾ���ļ���
		
		
		t.commit();
		session.close();
		
		
	}

	//����ͼƬ����
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

	//��ȡͼƬ�б�
	public List<Map> getPictureList(Integer id) {
		return AD.getPictureList(id);
	}
}
