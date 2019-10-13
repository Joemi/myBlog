package controller;

import java.util.HashMap;
import java.util.Map;

import entity.User;

public class UserLoginSet {
	private static UserLoginSet userLoginSet = new UserLoginSet();
	private Map<Integer, User> userLogin = new HashMap<Integer, User>();
	
	private UserLoginSet() {}
	
	public boolean isLogin(Integer key){
		if(userLogin.get(key)!= null){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setLogin(User user){
		userLogin.put(user.getId(), user);
	}
	
	public User getUser(Integer key){
		return userLogin.get(key);
	}
	
	public void reomveUser(Integer key){
		userLogin.remove(key);
	}
	
	public static UserLoginSet getuserLogin() {
		return userLoginSet;
	}
}
