package com.camsolute.code.camp.lib.contract.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.camsolute.code.camp.lib.data.CampSQL;

public interface DBHandler {
	
	public Connection dbConnection() throws SQLException;
	
	public void releaseDBConnection(Connection dbConnection) throws SQLException;
	public void releaseDBResultSet(ResultSet resultSet) throws SQLException;
	public void releaseDBStatement(Statement statement) throws SQLException;
	public void releaseDBPreparedStatement(PreparedStatement preparedStatement) throws SQLException;

	public abstract class AbstractDBHandler implements DBHandler {

  	protected static DataSource dataSource = null;

  	protected abstract void initializeDataSource();
  	
		public Connection dbConnection() throws SQLException {
//  			Connection connection = DriverManager.getConnection(CampSQL._DBLINK, CampSQL._USER, CampSQL._PASSWORD);
  		return dataSource.getConnection();
		}

		public void releaseDBConnection(Connection dbConnection) throws SQLException {
			// TODO Auto-generated method stub
			if(dbConnection != null)dbConnection.commit();
			if(dbConnection != null)dbConnection.close();
		}

		public void releaseDBStatement(Statement statement) throws SQLException {
			// TODO Auto-generated method stub
			if(statement != null) statement.close();
		}

		public void releaseDBPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
			// TODO Auto-generated method stub
			if(preparedStatement != null) preparedStatement.close();
		}
		
		public void releaseDBResultSet(ResultSet resultSet) throws SQLException {
			if(resultSet != null) resultSet.close();
		}
	}
	
	public class MariaDBHandler extends AbstractDBHandler {
		
		public MariaDBHandler(){
			initializeDataSource();
		}

		protected void initializeDataSource() {
  		if(dataSource ==null) {
        PoolProperties p = new PoolProperties();
        p.setConnectionProperties("useUnicode=true&characterEncoding=UTF-8;rewriteBatchedStatements=true");
        p.setName("CAMPDBPOOL");
        p.setUrl(CampSQL._DBLINK);
        p.setDriverClassName("org.mariadb.jdbc.Driver");
        p.setUsername(CampSQL._USER);
        p.setPassword(CampSQL._PASSWORD);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
  		}
		}
	}
}
