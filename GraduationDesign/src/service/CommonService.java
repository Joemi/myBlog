package service;

import java.util.List;
import java.util.Map;

import dao.CommonDao;

public class CommonService {
	
	CommonDao CD = new CommonDao();

	public List<Map> getParamListInfo(String paramCode) {
		return CD.getParamListInfo(paramCode);
	}

}
