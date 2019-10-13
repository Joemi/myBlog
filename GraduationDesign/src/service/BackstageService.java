package service;

import java.util.List;

import dao.BackstageDao;
import entity.User;

public class BackstageService {
	
	BackstageDao BD = new BackstageDao();

	//��ȡ�û�״̬������״̬���ز�ͬ��ֵ������Ա��
	public int login(String userId, String password) {
		List<User> listOne = BD.checkLogin(userId, password);
		List<User> listTwo = BD.checkLoginWithoutPW(userId);
		if(listTwo.size() == 0){
			return 0;	//δע��
		}
		else if(listOne.size() == 0 && listTwo.size() > 0){
			return 2;	//�û����������
		}
		else if(listOne.get(0).getStatus().equals("1")){
			return 3;	//�û�״̬�쳣
		}
		else{
			return 1;
		}
	}

	//��ȡ��¼�û���Ϣ������Ա��
	public List<User> loginInfo(String userId, String password) {
		List<User> list = BD.checkLogin(userId, password);
		return list;
	}

}
