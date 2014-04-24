//dburgess
//Working DBConnection object
//
//The user and password NEED to change at some point to something more secure

package com.pippin.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

//uses mybatis a SQL Mapping Framework for Java
//http://mirrors.ibiblio.org/maven2/org/mybatis/mybatis/3.2.3/
import org.apache.ibatis.jdbc.ScriptRunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBConnection {
	
	private ScriptRunner runner;
	private InputStream inStrm;
	private BufferedReader buffReader;
	
	private static DBConnection instance;
	private HikariDataSource hkds;

	public DBConnection(){
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", "localhost");
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", "kloudbook");
		config.addDataSourceProperty("user", "root");
		config.addDataSourceProperty("password", "root");
		hkds = new HikariDataSource(config);
	}
	
	public static DBConnection getInstance(){
		if(instance ==null ){
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		return hkds.getConnection();
	}

	// For SQL scripts
	public void prepareScript(String script) {
		try {
			runner = new ScriptRunner(getConnection());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		inStrm = DBConnection.class.getResourceAsStream(script);
		buffReader = null;
		try {
			buffReader = new BufferedReader(new InputStreamReader(inStrm, "UTF-8"));
			runner.setLogWriter(null);
			runner.runScript(buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			if (runner != null){
				runner.closeConnection();
				inStrm.close();
				buffReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Disconnected from database");
	}

}
