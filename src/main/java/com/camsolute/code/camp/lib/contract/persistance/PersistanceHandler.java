package com.camsolute.code.camp.lib.contract.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.persistance.DBHandler.MysqlDBHandler;


import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.contract.core.CampStates.IOAction;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.models.order.OrderList;
import com.camsolute.code.camp.lib.utilities.Util;

public interface PersistanceHandler {
	
	public void loadById(int id);
	
  public void loadByBusinessId();
  
  public void loadListByBusinessKey();
  
  public void loadByGroup(); // returns a list of business objects that belong to a specific group
  
  public void loadByGroupVersion();
  
	public void loadDate(String businessId, String date);

	public void loadDateRange(String businessId, String startDate, String endDate);

	public void loadDate(String date);

	public void loadDateRange(String startDate, String endDate);
	
  public  void save();

  public void update();
  
  public boolean hasHistory();
  
  public HistoryHandler historyHandler();
  
  public void rsToI(ResultSet rs) throws SQLException;

  public abstract class PersistanceHandlerImpl implements PersistanceHandler {
  	
  	protected boolean hasHistory = false;
  	
  	protected HistoryHandler historyHandler;
  	
  	//encapsulates DB call specifics 
  	protected DBHandler dbHandler;
  	
  	protected DBHandler dbHandler() { 
  		return dbHandler; 
  	}
  	
  	// encapsulates DB SQL specifics
  	 	
  	public boolean hasHistory() {
  		return hasHistory;
  	}
  	
  	public HistoryHandler historyHandler() {
  		if(hasHistory) {
  			return historyHandler;
  		}
  		return null;
  	}
  	
  	public abstract void initialize();
  	
  }  
}
