package com.camsolute.code.camp.lib;

import java.sql.SQLException;

import com.camsolute.code.camp.core.interfaces.HasHistory;
import com.camsolute.code.camp.core.interfaces.HasStatus;
import com.camsolute.code.camp.core.interfaces.UpdateInstanceInterface;
import com.camsolute.code.camp.core.types.CampHistory;

public interface CampHistoryDaoInterface<T extends HasStatus> {
	public int addInstance(int instanceId, String businessId, T instance, CampHistory<?> history) throws SQLException;
	public int addInstances(int[] instanceId, String[] businessId, T[] instance, CampHistory<?>[] history) throws SQLException;
	/**
	 * Load the most recent persisted business object instance from the Database based on its businessId.
	 * Only the most current instance entry (=>instanceId=currentInstanceId) is loaded.
	 * @param businessId
	 * @return current instance of the business persisted business object
	 * @throws SQLException
	 */
	public T loadCurrent(String businessId) throws SQLException;
	public T loadFirst(String businessId) throws SQLException;
	public T loadPrevious(String businessId, CampHistory<?> history) throws SQLException;
	public T loadNext(String businessId, CampHistory<?> history) throws SQLException;
	public int createTable(boolean checkDBExists);
}
