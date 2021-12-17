package com.store.dao;



import java.util.List;
import java.util.Map;

import com.store.domain.Product;
import com.store.utils.PageBean;

public interface DemoDao {
	public int createUser(String userId,String userName);
	public Map<String, Object> getOneUser(String userId);
	public List<Map<String, Object>> findUsers();
	public PageBean findInfosByPage(int pageNumber, int pageSize, String productName);
	
	public List<Product> findProducts();
	public Map<String, Object> getOneProduct(Long pid);
	
	
	
}
