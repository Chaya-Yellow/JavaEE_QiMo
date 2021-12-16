package com.store.dao;

import java.util.List;
import java.util.Map;

import com.store.utils.PageBean;

public class DemoDaoImpl extends AbstractDao implements DemoDao {

	@Override
	public int createUser(String userId, String userName) {
		String sql = "insert into user(user_id,user_name,password,state,is_admin) values(?,?,?,?,?)";
		return this.update(sql, new Object[] {userId, userName,"123456","new","N"});
	}

	@Override
	public Map<String, Object> getOneUser(String userId) {
		String sql = "select user_id,user_name from user where user_id=?";
		return this.queryOne(sql, new Object[] {userId});
	}

	@Override
	public List<Map<String, Object>> findUsers() {
		String sql = "select user_id,user_name from user";
		return this.queryList(sql, new Object[] {}, null);
	}

	@Override
	public PageBean findInfosByPage(int pageNumber, int pageSize) {
		String sql = "select * from product";
		return this.queryByPage(sql, new Object[] {}, pageNumber, pageSize, null);
	}

}
