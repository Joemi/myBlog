package service;

import java.util.List;

import dao.BackstageDao;
import entity.User;

public class BackstageService {
	
	BackstageDao BD = new BackstageDao();

	//获取用户状态，根据状态返回不同的值（管理员）
	public int login(String userId, String password) {
		List<User> listOne = BD.checkLogin(userId, password);
		List<User> listTwo = BD.checkLoginWithoutPW(userId);
		if(listTwo.size() == 0){
			return 0;	//未注册
		}
		else if(listOne.size() == 0 && listTwo.size() > 0){
			return 2;	//用户名密码错误
		}
		else if(listOne.get(0).getStatus().equals("1")){
			return 3;	//用户状态异常
		}
		else{
			return 1;
		}
	}

	//获取登录用户信息（管理员）
	public List<User> loginInfo(String userId, String password) {
		List<User> list = BD.checkLogin(userId, password);
		return list;
	}

}
