package com.store.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcTools {
	private static DataSource dataSource;//新建数据池
    static {
        dataSource=new ComboPooledDataSource("store-c3p0");
        //连接池c3p0数据连接池
    }
    public static Connection getConnection(){
        Connection connection=null;

        try {
            connection=dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void release(Connection connection, Statement[] statements, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (statements != null)
			for (Statement statement : statements) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    
    public static void main(String[] args) {
    	String _USER_ID="root";
    	String _PASSWORD = "root";
    	String _DB_URL = "jdbc:mysql://127.0.0.1:3306/store-hk?useUnicode=true&characterEncoding=utf-8&useSSL=false"; 

    	Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DataSource ds = new ComboPooledDataSource();
			
			connection = ds.getConnection();
	//		3. 创建一个Statement
			statement = connection.createStatement();
	//		4. 执行sql
			String sql = "select user_id,user_name from users";
			rs = statement.executeQuery(sql);
	//		5.处理结果集(如果是查询)
			while(rs.next()) {
				System.out.println("用户ID:"+rs.getString("user_id")+" 用户名："+rs.getString("user_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			6.释放资源。(Connection、Statement、ResultSet)
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
