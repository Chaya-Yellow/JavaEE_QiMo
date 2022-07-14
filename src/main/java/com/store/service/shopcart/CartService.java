package com.store.service.shopcart;

import com.store.domain.Product;
import com.store.domain.ShopItem;
import com.store.utils.JdbcTools;
import com.store.utils.PageBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartService {
	public int createUser(String userId, String userName);
	public Map<String, Object> getOneUser(String userId);
	public List<Map<String, Object>> findUsers();

    PageBean findInfosByPage(int pageNumber, int pageSize, String productName);

    public static int createOrder(String[] shopChks, String[] shopNums, Long uid) {
		return 0;
	}

	public static HashMap<String, ShopItem> findProducts() throws SQLException {
		Connection connection = JdbcTools.getConnection();
		Statement statement=connection.createStatement();
		ResultSet res= statement.executeQuery("select * from product");
		HashMap<String, ShopItem> carts = new HashMap<String, ShopItem>();
		while (res.next()) {
//			Map<String, Object>[] products = null;
//			for (Map<String, Object> productMap : products) {

				ShopItem shopItem = new ShopItem();
				Product product = new Product();
				product.setPid(res.getLong("pid"));
				product.setPname(res.getString("pname"));
				product.setpImage(res.getString("pimage"));
				product.setMarketPrice(res.getDouble("market_price"));
				product.setShopPrice(res.getDouble("shop_price"));
				product.setCid(res.getLong("cid"));
				product.setpDesc(res.getString("pdesc"));
				product.setpState(res.getString("pstate"));

				shopItem.setProduct(product);
				shopItem.setShopAmount(1);

				carts.put(res.getString("pid"), shopItem);
//			}
		}
		JdbcTools.release(connection,statement,res);
		return carts;
	}


	}


