package service;

import java.util.UUID;

import dao.RegisterDao;

public class RegisterService {

	RegisterDao RD = new RegisterDao();
	
	public void registerUser(String userName,String password){
		RD.register(userName, password);
	}
	
}
