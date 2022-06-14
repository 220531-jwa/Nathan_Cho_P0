package dev.cho.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionUtil {
	
	private static ConnectionUtil cu;
	private static Properties dbProps;
	
	private ConnectionUtil() {
		dbProps = new Properties();
		
		InputStream props = ConnectionUtil.class.getClassLoader().getResourceAsStream("connection.properties");
		
		try {
			dbProps.load(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		
		if(cu == null) {
			cu = new ConnectionUtil();
		}
		
		return cu;
		
	}
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		//get our credentials from our ConnectionUtil's properties object that we created in the constructor
		
		try {
			Class.forName(dbProps.getProperty("driver"));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		
		
		String url = dbProps.getProperty("url");
		String username = dbProps.getProperty("username");
		String password = dbProps.getProperty("password");
		
		//use those credentials + Driver Manager to connect to our database
		
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) { //remove this later since it's not good coding to keep this runnable
		Connection connection = getConnectionUtil().getConnection();
		
		if(connection != null) {
			System.out.println("Connection Success");
		}
		else
		{
			System.out.println("Connection Failed");
		}
		
		try {
			connection.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
