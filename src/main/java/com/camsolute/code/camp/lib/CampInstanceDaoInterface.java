package com.camsolute.code.camp.lib;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.camsolute.code.camp.lib.CampInstance;

public interface CampInstanceDaoInterface<T extends HasResultSetToInstance<T>> {

  /**
   * Returns the database scheme name.
   *
   * @return
   */
  public String dbName();

  /**
   * Returns the database table name in which the object instance our CampInstance belongs to is
   * persisted.
   *
   * @return
   */
  public String table();

  /**
   * Returns the database table definition (see: <code>com.camsolute.code.lib.data.CampSQL</code>)
   * of the object instance Campinstance belongs to.
   *
   * @return
   */
  public String[][] tabledef();

  /**
   * Returns the database table name in which CampInstance is persisted.
   *
   * @return
   */
  public String itable();

  public int addInstance(int instanceId, String businessId, T instance, CampHistory<?> history)
      throws SQLException;

  public int addInstances(
      int[] instanceId, String[] businessId, T[] instance, CampHistory<?>[] history)
      throws SQLException;
  /**
   * Load the most recent persisted business object instance of Type <code>T</code> from the Database based on its
   * businessId. Only the most current instance entry (:= instanceId==currentInstanceId) is loaded.
   *
   * @param businessId
   * @return current instance of the business persisted business object
   * @throws SQLException
   */
  public T loadCurrent(String businessId) throws SQLException;

  public T loadFirst(String businessId) throws SQLException;

  public T loadPrevious(String businessId, CampHistory<?> history) throws SQLException;

  public T loadNext(String businessId, CampHistory<?> history) throws SQLException;

  public int createTable(boolean checkDBExists);

  public CampInstance rsToI(ResultSet rs, boolean log) throws SQLException;
}
