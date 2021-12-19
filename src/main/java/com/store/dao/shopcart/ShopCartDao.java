package com.store.dao.shopcart;

import com.store.domain.Order;

import java.util.List;
import java.util.Map;

import com.store.domain.Product;
import com.store.utils.PageBean;

public interface ShopCartDao {
	public int createOrder(Order order);
	public List<Product> findProducts();
	public Map<String, Object> getOneProduct(Long pid);
}
