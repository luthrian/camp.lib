package com.camsolute.code.camp.lib.contract.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.utilities.Util;

public interface DBHandler {
	
  public void executeUpdate();
  
  public ResultSet executeQuery(String sql);
  
  public void executeInsert();
  
 public class MysqlDBHandler implements DBHandler {
	 
	 private PersistanceHandler persistanceHandler;

	 public MysqlDBHandler(PersistanceHandler persistanceHandler) {
		 this.persistanceHandler = persistanceHandler;
	 }
	 
		public void executeUpdate() {
			// TODO Auto-generated method stub
			
		}

		public ResultSet executeQuery(String sql) {
  		Connection conn = null;
  		ResultSet rs = null;
  		Statement dbs = null;
  		try{
  			//TODO: Make this Mysql specific
  			conn = Util.DB.__conn();  			
  			dbs = conn.createStatement();  			  			
  			rs = dbs.executeQuery(sql);		
  			  			
  		} catch(SQLException e) {
  			e.printStackTrace();
  		} finally {
  			Util.DB.release(conn);
  			Util.DB.releaseStatement(dbs);
  		}			
  		return rs;
		}

		public void executeInsert() {
			// TODO Auto-generated method stub
			
		}
		
 }
}
