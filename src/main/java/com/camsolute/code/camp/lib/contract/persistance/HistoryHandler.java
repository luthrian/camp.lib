package com.camsolute.code.camp.lib.contract.persistance;

import java.util.ArrayList;

import com.camsolute.code.camp.lib.models.CampInstance;
import com.camsolute.code.camp.lib.models.order.Order;

public interface HistoryHandler {
	
	public void instanceLoad(String select, boolean primary,boolean log);

	public void instanceListLoad(String select, boolean primary, boolean log);
	
	public void loadFirst(String businessId);

	public void loadCurrent(String businessId);

	public void loadPrevious();

	public void loadNext();

  public String dbName(boolean primary);

  public String table(boolean primary);

  public String[][] tabledef(boolean primary);

	public String updatestable(boolean primary);

	public String[][] updatestabledef(boolean primary);
	
	public String businessIdColumn(boolean primary);

	public class OrderHistoryHandler implements HistoryHandler {

		private CampInstance myHistory;
		
		private PersistanceHandler persistanceHandler;
		
		public OrderHistoryHandler(CampInstance orderHistory, PersistanceHandler persistanceHandler) {
			myHistory = orderHistory;
			this.persistanceHandler = persistanceHandler; 
		}
		
		public void loadFirst(String businessId) {
			// TODO Auto-generated method stub
			
		}
		public void loadCurrent(String businessId) {
			// TODO Auto-generated method stub
			
		}
		public void loadPrevious() {
			// TODO Auto-generated method stub
			
		}
		public void loadNext() {
			// TODO Auto-generated method stub
			
		}

		public void instanceLoad(String select, boolean primary, boolean log) {
			// TODO Auto-generated method stub
			
		}

		public void instanceListLoad(String select, boolean primary, boolean log) {
			// TODO Auto-generated method stub
			
		}

		public String dbName(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}

		public String table(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}

		public String[][] tabledef(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}

		public String updatestable(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}

		public String[][] updatestabledef(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}

		public String businessIdColumn(boolean primary) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
