package com.camsolute.code.camp.lib.contract.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.camsolute.code.camp.lib.contract.persistance.DBHandler.MysqlDBHandler;
import com.camsolute.code.camp.lib.contract.persistance.SQLHandler.OrderMysqlSQLHandler;


import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.CampStatesInterface.IOAction;
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
  	protected SQLHandler sqlHandler;
  	
  	protected SQLHandler sqlHandler() {
  		return sqlHandler;
  	}
  	 	
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
  
  public class OrderPersistanceHandler extends PersistanceHandlerImpl {

  	private Order myOrderReference;
  	
  	private OrderList myOrderList = new OrderList();
  	  	
  	public OrderPersistanceHandler(Order order) {
  		myOrderReference = order; // set reference to the business Object
  		initialize();
  	}
  			
  	public void loadById(int id) {
  		long startTime = System.currentTimeMillis();
  		String _f = "[loadById]";
  		Util.Log.logEnter(_f,"load a persisted order object instance from the database by id", this.getClass());
  		
  		ResultSet rs = null;
  	
  		int retVal = 0;
  		try{
  			rs = dbHandler().executeQuery(sqlHandler().loadByIdSQL(id));
 				rsToI(rs);
 				myOrderReference.setHistory(CampInstanceDao.instance().rsToI(rs,!Util._IN_PRODUCTION));
 				myOrderReference.states().ioAction(IOAction.LOAD);
  			retVal =1;  			
  			Util.Log.log(_f,"'"+retVal+"' entr"+((retVal!=1)?"ies":"y")+" loaded",this.getClass());
  			
  		} catch(SQLException e) {
  			Util.Log.log(_f,"SQLException! database transaction failed.",this.getClass());
  			e.printStackTrace();
  		} finally {
  			Util.Log.log(_f,"releasing resultset",this.getClass());
  			Util.DB.releaseRS(rs);
  		}
		}

		public void loadByBusinessId() {
			// TODO Auto-generated method stub
			
		}

		public void loadListByBusinessKey() {
			// TODO Auto-generated method stub
			
		}

		public void loadByGroup() {
			// TODO Auto-generated method stub
			
		}

		public void loadByGroupVersion() {
			// TODO Auto-generated method stub
			
		}

		public void loadDate(String businessId, String date) {
			// TODO Auto-generated method stub
			
		}

		public void loadDateRange(String businessId, String startDate, String endDate) {
			// TODO Auto-generated method stub
			
		}

		public void loadDate(String date) {
			// TODO Auto-generated method stub
			
		}

		public void loadDateRange(String startDate, String endDate) {
			// TODO Auto-generated method stub
			
		}
  	
		public void save() {
			// TODO Auto-generated method stub
			
		}

		public void update() {
			// TODO Auto-generated method stub
			
		}

		public void setDBHandler(DBHandler handler) {
			dbHandler = handler;			
		}

		@Override
		public void initialize() {
			//TODO: load from preference setting
			dbHandler = new MysqlDBHandler(this);
			sqlHandler = new OrderMysqlSQLHandler();
			
		}
		
		public void rsToI(ResultSet rs) throws SQLException {
			myOrderReference.updateId(rs.getInt(sqlHandler.tabledef()[0][0]));
			myOrderReference.setBusinessId(rs.getString("order_number"));
			myOrderReference.setBusinessKey(rs.getString("order_businesskey"));
			myOrderReference.setDate(rs.getTimestamp("order_date"));
			myOrderReference.setByDate(rs.getTimestamp("order_by_date"));
			myOrderReference.setStatus(rs.getString("_status"));			
		}


		// TODO: public Order updateAttribute(Order.UpdateAttribute attribute, String businessId, String newValue, boolean log);
		
  }
}
