package com.store.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.store.utils.JdbcTools;
import com.store.utils.PageBean;
import com.store.utils.StoreConstants;
/**
 * 
 * @author huangwi
 * This abstract class is for database accessing operation, 
 * such as query/insert/update/delete and paging.
 * Usage: target class must extends this abstract class with protected access rights.
 *
 */
public abstract class AbstractDao {
	
	/**
	 * For insert/update/delete operation
	 * @param sql
	 * @param params
	 * @return
	 */
	protected int update(String sql, Object[] params) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int amt = 0;
		try {
			connection = JdbcTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			int idx = 0;
			for (int i=0;i<params.length;i++) {
				idx = i+1;
				if (params[i] instanceof String) {
					preparedStatement.setString(idx, String.valueOf(params[i]));
				} else if (params[i] instanceof Date) {
					Date date = (Date) params[i];
					preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
				} else if (params[i] instanceof Integer) {
					preparedStatement.setInt(idx, Integer.parseInt(String.valueOf(params[i])));
				} else if (params[i] instanceof Double) {
					preparedStatement.setDouble(idx, Double.parseDouble(String.valueOf(params[i])));
				} else if (params[i] instanceof Long) {
					preparedStatement.setLong(idx, Long.parseLong(String.valueOf(params[i])));
				} else {
					preparedStatement.setObject(idx, params[i]);
				}
			}
			
			amt = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.release(connection, preparedStatement, null);
		}
		return amt;
	}
	
	/**
	 * Only for record size calculating
	 * @param sql
	 * @param params
	 * @return
	 */
	protected int countRecord(String sql, Object[] params) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = JdbcTools.getConnection();
			sql = sql.replace("FROM", "from").replace("From", "from");
			sql = "select count(1) "+ sql.substring(sql.indexOf("from"), sql.length());
			preparedStatement = connection.prepareStatement(sql);
			for (int i=0;i<params.length;i++) {
				if (params[i] instanceof String) {
					preparedStatement.setString(i+1, String.valueOf(params[i]));
				} else if (params[i] instanceof Date) {
					Date date = (Date) params[i];
					preparedStatement.setDate(i+1, new java.sql.Date(date.getTime()));
				} else if (params[i] instanceof Integer) {
					preparedStatement.setInt(i+1, Integer.parseInt(String.valueOf(params[i])));
				} else if (params[i] instanceof Double) {
					preparedStatement.setDouble(i+1, Double.parseDouble(String.valueOf(params[i])));
				} else if (params[i] instanceof Long) {
					preparedStatement.setLong(i+1, Long.parseLong(String.valueOf(params[i])));
				} else {
					preparedStatement.setObject(i, params[i]);
				}
			}
			
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.release(connection, preparedStatement, rs);
		}
		return 0;
	}
	
	/**
	 * For one record query
	 * @param sql
	 * @param params
	 * @return
	 */
	protected Map<String, Object> queryOne(String sql, Object[] params) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Map<String, Object> results = new HashMap<String,Object>();
		try {
			connection = JdbcTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i=0;i<params.length;i++) {
				if (params[i] instanceof String) {
					preparedStatement.setString(i+1, String.valueOf(params[i]));
				} else if (params[i] instanceof Date) {
					Date date = (Date) params[i];
					preparedStatement.setDate(i+1, new java.sql.Date(date.getTime()));
				} else if (params[i] instanceof Integer) {
					preparedStatement.setInt(i+1, Integer.parseInt(String.valueOf(params[i])));
				} else if (params[i] instanceof Double) {
					preparedStatement.setDouble(i+1, Double.parseDouble(String.valueOf(params[i])));
				} else if (params[i] instanceof Long) {
					preparedStatement.setLong(i+1, Long.parseLong(String.valueOf(params[i])));
				} else {
					preparedStatement.setObject(i+1, params[i]);
				}
			}
			
			rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			if(rs.next()) {
				for (int i=1;i<=rsmd.getColumnCount();i++) {
					String columnName = rsmd.getColumnName(i);
					results.put(columnName, rs.getObject(columnName));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.release(connection, preparedStatement, rs);
		}
		return results;
	}
	
	/**
	 * For multiple records query
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<Map<String, Object>> queryList(String sql, Object[] params, Integer[] searchType) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		try {
			connection = JdbcTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			int idx = 1;
			for (int i=0;i<params.length;i++) {
				if (params[i] instanceof String) {
					if (searchType != null) {
						if (StoreConstants.SEARCH_TYPE_WILDCARD == searchType[i] && !StringUtils.isEmpty(String.valueOf(params[i]))) {
							preparedStatement.setString(idx, "%" + String.valueOf(params[i]) + "%");
						} else {
							preparedStatement.setString(idx, String.valueOf(params[i]));
						}
					} else {
						preparedStatement.setString(idx, String.valueOf(params[i]));
					}
				} else if (params[i] instanceof Date) {
					Date date = (Date) params[i];
					if (searchType != null) {
						if (StoreConstants.SEARCH_TYPE_BETWEEN == searchType[i] && date != null) {
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
							idx++;
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
						} else {
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
						}
					} else {
						preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
					}
				} else if (params[i] instanceof Integer) {
					preparedStatement.setInt(idx, Integer.parseInt(String.valueOf(params[i])));
				} else if (params[i] instanceof Double) {
					preparedStatement.setDouble(idx, Double.parseDouble(String.valueOf(params[i])));
				} 
				
				idx++;
			}
			
			rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while(rs.next()) {
				Map<String, Object> results = new HashMap<String,Object>();
				for (int i=1;i<=rsmd.getColumnCount();i++) {
					String columnName = rsmd.getColumnName(i);
					results.put(columnName, rs.getObject(columnName));
				}
				resultList.add(results);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.release(connection, preparedStatement, rs);
		}
		return resultList;
	}
	/**
	 * Query records with paging
	 * @param sql
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	protected PageBean queryByPage(String sql, Object[] params, int pageNumber, int pageSize, Integer[] searchType) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		PageBean pageBean = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		try {
			connection = JdbcTools.getConnection();
			
			String pagingSql = sql + " limit ?,?";
			preparedStatement = connection.prepareStatement(pagingSql);
			
			int paramSize = params.length;
			int idx = 1;
			for (int i=0;i<paramSize;i++) {
				if (params[i] instanceof String) {
					if (searchType != null) {
						if (searchType[i] != null && StoreConstants.SEARCH_TYPE_WILDCARD == searchType[i] && !StringUtils.isEmpty(String.valueOf(params[i]))) {
							preparedStatement.setString(idx, "%" + String.valueOf(params[i]) + "%");
						} else {
							preparedStatement.setString(idx, String.valueOf(params[i]));
						}
					} else {
						preparedStatement.setString(idx, String.valueOf(params[i]));
					}
				} else if (params[i] instanceof Date) {
					Date date = (Date) params[i];
					if (searchType != null) {
						if (StoreConstants.SEARCH_TYPE_BETWEEN == searchType[i] && date != null) {
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
							idx++;
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
						} else {
							preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
						}
					} else {
						preparedStatement.setDate(idx, new java.sql.Date(date.getTime()));
					}
				} else if (params[i] instanceof Integer) {
					preparedStatement.setInt(idx, Integer.parseInt(String.valueOf(params[i])));
				} else if (params[i] instanceof Double) {
					preparedStatement.setDouble(idx, Double.parseDouble(String.valueOf(params[i])));
				} 
				
				idx++;
			}
			
			preparedStatement.setInt(paramSize+1, (pageNumber-1)*pageSize);
			preparedStatement.setInt(paramSize+2, pageSize);
			
			rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnSize = rsmd.getColumnCount();
			while(rs.next()) {
				Map<String, Object> results = new HashMap<String,Object>();
				for (int i=1;i<=columnSize;i++) {
					String columnName = rsmd.getColumnName(i);
					results.put(columnName, rs.getObject(columnName));
				}
				resultList.add(results);
			}
			
			int totalRecords = countRecord(sql, params);
			
			pageBean = new PageBean(pageNumber, totalRecords, pageSize);
			pageBean.setList(resultList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.release(connection, preparedStatement, rs);
		}
		return pageBean;
	}
}
