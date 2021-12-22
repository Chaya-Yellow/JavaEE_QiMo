package com.store.service.shopcart;

import com.store.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface CartService {
	public int createUser(String userId, String userName);
	public Map<String, Object> getOneUser(String userId);
	public List<Map<String, Object>> findUsers();

	public static int createOrder(String[] shopChks, String[] shopNums, Long uid) {
		return 0;
	}

	public static List findProducts() {
		return null;
	}
}

