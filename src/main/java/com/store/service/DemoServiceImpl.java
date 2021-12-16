package com.store.service;

import java.util.List;
import java.util.Map;

import com.store.dao.DemoDao;
import com.store.dao.DemoDaoImpl;
import com.store.utils.PageBean;

public class DemoServiceImpl implements DemoService {
	private DemoDao demoDao;
	public DemoServiceImpl() {
		demoDao = new DemoDaoImpl();
	}

	@Override
	public int createUser(String userId, String userName) {
		return demoDao.createUser(userId, userName);
	}

	@Override
	public Map<String, Object> getOneUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageBean findInfosByPage(int pageNumber, int pageSize) {
		return demoDao.findInfosByPage(pageNumber, pageSize);
	}

}
