package service;

import java.util.List;
import java.util.Map;

import dao.UserManageDao;

public class UserManageService {
	
	UserManageDao UMD = new UserManageDao();

	public List<Map> getUserManageInfo(String userName, Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		return UMD.getUserManageInfo(userName,index,length);
	}

	public Integer getUserManageCont(String userName) {
		return UMD.getUserManageCont(userName);
	}

	public void changeUserStatus(Integer userId, Integer status) {
		UMD.changeUserStatus(userId,status);
		
	}

}
