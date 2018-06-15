package com.camsolute.code.camp.lib.contract.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.models.CampInstanceDao;
import com.camsolute.code.camp.lib.models.order.Order;
import com.camsolute.code.camp.lib.utilities.Util;

public interface SQLHandler {
  /**
   * Returns the database scheme name.
   *
   * @return data base name
   */
  public String dbName();

  /**
   * Returns the database table name in which the object instance our CampInstance belongs to is
   * persisted.
   *
   * @return table name
   */
  public String table();

  /**
   * Returns the database table definition (see: <code>com.camsolute.code.lib.data.CampSQL</code>)
   * of the object instance CampInstance belongs to.
   *
   * @return table definition
   */
  public String[][] tabledef();

	public String updatestable();

	public String[][] updatestabledef();

	public String loadByIdSQL(int id);
	
  public void loadByBusinessIdSQL();
  
  public void loadListByBusinessKeySQL();
  
  public void loadByGroupSQL(); // returns a list of business objects that belong to a specific group
  
  public void loadByGroupVersionSQL();
  
	public void loadDateSQL(String businessId, String date);

	public void loadDateRangeSQL(String businessId, String startDate, String endDate);

	public void loadDateSQL(String date);

	public void loadDateRangeSQL(String startDate, String endDate);
	
  public  void saveSQL();

  public void updateSQL();
  
  //TODO: factor out?
  public class OrderMysqlSQLHandler implements SQLHandler {
  	  	
		public String dbName() { return CampSQL.database[CampSQL._ORDER_DB_INDEX];}

		public String table() { return CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_TABLE_INDEX);}

		public String[][] tabledef() { return CampSQL.Order.order_table_definition;}

		public String updatestable() { return CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_UPDATES_TABLE_INDEX);}
		
		public String[][] updatestabledef() { return CampSQL.Order.order_updates_table_definition;}

		private String ohptable() { return CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_PROCESS_TABLE_INDEX);}

		private String[][] ohptabledef() { return CampSQL.Order.order_has_process_table_definition;}

		private String ohoptable() { return CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_ORDER_POSITION_TABLE_INDEX);}

		private String[][] ohoptabledef() { return CampSQL.Order.order_has_order_position_table_definition;}

		private String ohotable() { return CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL.Order._ORDER_HAS_ORDER_TABLE_INDEX);}

		private String[][] ohotabledef() { return CampSQL.Order.order_has_order_table_definition;}
		  	
		public String insertValues() {
			// TODO Auto-generated method stub
			return null;
		}

		public String insertUpdateValues(String target) {
			// TODO Auto-generated method stub
			return null;
		}

		public String formatUpdateSQL(String SQL) {
			// TODO Auto-generated method stub
			return null;
		}

		public String loadByIdSQL(int id) {
			//TODO: use SQLHandler for CampInstanceDao also
			
  		String SQL = "SELECT * FROM "+table()+" AS t, "+CampInstanceDao.table+" AS ti WHERE "
  				+ "t.`"+tabledef()[0][0]+"`="+id
  				+ " AND t.`"+tabledef()[0][0]+"`=ti.`_object_id`"
  				+ " AND ti.`_instance_id`=ti.`_current_instance_id`";
  		
  		return SQL;
		}

		@Override
		public void loadByBusinessIdSQL() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadListByBusinessKeySQL() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadByGroupSQL() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadByGroupVersionSQL() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadDateSQL(String businessId, String date) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadDateRangeSQL(String businessId, String startDate, String endDate) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadDateSQL(String date) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadDateRangeSQL(String startDate, String endDate) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void saveSQL() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateSQL() {
			// TODO Auto-generated method stub
			
		}
		
  }
}
