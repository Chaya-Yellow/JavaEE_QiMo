package com.store.dao.shopcart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
import com.store.utils.JdbcTools;

public class ShopCartDaoImpl implements ShopCartDao {

	@Override
	public int createOrder(Order order) {
		Connection connection = null;
		PreparedStatement preparedStatement4Order = null;
		PreparedStatement preparedStatement4OrderItem = null;
		int amt = 0;
		ResultSet idRs = null;
		try {
			connection = JdbcTools.getConnection();
			connection.setAutoCommit(false);
			String orderSql = "insert into orders(ordertime,total,state,address,name,telephone,uid) values(?,?,?,?,?,?,?)";
			preparedStatement4Order = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement4Order.setDate(1, new java.sql.Date(order.getOrderTime().getTime()));
			preparedStatement4Order.setDouble(2, order.getTotal());
			preparedStatement4Order.setString(3, order.getState());
			preparedStatement4Order.setString(4, order.getAddress());
			preparedStatement4Order.setString(5, order.getName());
			preparedStatement4Order.setString(6, order.getTelePhone());
			preparedStatement4Order.setLong(7, order.getUid());
			
			amt = preparedStatement4Order.executeUpdate();
			
			idRs = preparedStatement4Order.getGeneratedKeys();
			Long orderId = 0L;
			if (idRs.next()) {
				orderId = idRs.getLong(1);
			}
			
			List<OrderItem> orderItems = order.getItems();
			String orderItemSql = "insert into orderitem(quantity,total,pid,oid) values(?,?,?,?)";
			preparedStatement4OrderItem = connection.prepareStatement(orderItemSql);
			for (OrderItem item : orderItems) {
				preparedStatement4OrderItem.setInt(1, item.getQuantity());
				preparedStatement4OrderItem.setDouble(2, item.getTotal());
				preparedStatement4OrderItem.setLong(3, item.getPid());
				preparedStatement4OrderItem.setLong(4, orderId);
				preparedStatement4OrderItem.addBatch();
			}
			
			preparedStatement4OrderItem.executeBatch();
			
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JdbcTools.release(connection, new PreparedStatement[] {preparedStatement4Order,preparedStatement4OrderItem}, idRs);
		}
		return amt;
	}

	@Override
	public List<Product> findProducts() {
		return null;
	}

	@Override
	public Map<String, Object> getOneProduct(Long pid) {
		return null;
	}

}
