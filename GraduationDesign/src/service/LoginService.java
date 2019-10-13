package service;

import java.util.List;

import dao.LoginDao;
import entity.User;

public class LoginService {

	LoginDao LD = new LoginDao();
	
	public int login(String userName,String password){
		List<User> listOne = LD.checkLogin(userName, password);
		List<User> listTwo = LD.checkLoginWithoutPW(userName);
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
	
	public List<User> loginInfo(String userName,String password){
		List<User> list = LD.checkLogin(userName, password);
		return list;
	}
}
