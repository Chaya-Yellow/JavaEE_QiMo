package com.store.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.store.dao.DemoDao;
import com.store.dao.DemoDaoImpl;
import com.store.dao.shopcart.ShopCartDaoImpl;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.utils.PageBean;
import com.store.utils.StoreConstants;

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
	public PageBean findInfosByPage(int pageNumber, int pageSize, String productName) {
		return demoDao.findInfosByPage(pageNumber, pageSize, productName);
	}
	
	public int createOrder(String[] shopChks, String[] shopNums, Long uid) {
		Order order = new Order();
		
		order.setOrderTime(new Date());
		order.setState(StoreConstants.ORDER_STATE_NEW);
		order.setUid(uid);
		
		double sum = 0;
		for (int i=0;i<shopChks.length;i++) {
			String productId = shopChks[i];
			String shopNum = shopNums[i];
			if (!StringUtils.isEmpty(productId) && !StringUtils.isEmpty(shopNum) && Integer.parseInt(shopNum) > 0) {
				Map<String, Object> product = demoDao.getOneProduct(Long.parseLong(productId));
				double shopPrice = Double.parseDouble(product.get("shop_price").toString());
				
				OrderItem item = new OrderItem();
				item.setPid(Long.parseLong(productId));
				item.setQuantity(Integer.parseInt(shopNum));
				item.setTotal(shopPrice*Integer.parseInt(shopNum));
				sum += shopPrice*Integer.parseInt(shopNum);
				
				order.getItems().add(item);
			}
		}
		
		order.setTotal(sum);
		
		if (sum == 0) {
			return 0;
		} else {
			return new ShopCartDaoImpl().createOrder(order);
		}
	}
	
	public List findProducts() {
		return demoDao.findProducts();
	}
}
