package service;

import java.util.List;
import java.util.Map;

import dao.IndexDao;

public class IndexService {
	
	IndexDao ID = new IndexDao();

	public List<Map> getIndexListInfo(Integer num, Integer currentPage,
			Integer pageNumber) {
		Integer index = 0;
		if(currentPage > 1){
			index = (currentPage-1)*pageNumber;
		}
		Integer length = pageNumber;
		return ID.getIndexListInfo(num,index,length);
	}

	public Integer getIndexListCount(Integer num){
		return ID.getIndexListCount(num);
	}

}
