package com.camsolute.code.camp.lib;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.camsolute.code.camp.lib.CampInstanceDaoInterface;
import com.camsolute.code.camp.lib.InstanceId;
import com.camsolute.code.camp.lib.data.CampSQL;
import com.camsolute.code.camp.lib.contract.HasResultSetToInstance;
import com.camsolute.code.camp.lib.contract.HasStatus;
import com.camsolute.code.camp.lib.contract.IsInstanceDao;
import com.camsolute.code.camp.lib.exceptions.InitialIdInstantiationWithNullValueException;
import com.camsolute.code.camp.lib.utilities.Util;

public class CampInstance<T> implements CampInstanceInterface {

  private Timestamp timestamp;

  private InstanceId id;

  private InstanceId initialId;

  private InstanceId currentId;

  public CampInstance() {

    this.timestamp = Util.Time.timestamp();

    this.id = new InstanceId();

    InstanceId initialId = null;

    InstanceId currentId = null;

    currentId = new InstanceId(this.id.id());

    initialId = new InstanceId(this.id.id());

    this.initialId = initialId;

    this.currentId = currentId;
  }

  public CampInstance(String instanceId, String currentInstanceId, String initialInstanceId) {

    this.timestamp = Util.Time.timestamp();

    this.id = new InstanceId(instanceId);

    this.initialId = new InstanceId(initialInstanceId);

    this.currentId = new InstanceId(currentInstanceId);
  }

  public CampInstance(
      InstanceId instanceId, InstanceId currentInstanceId, InstanceId initialInstanceId) {
    this.timestamp = Util.Time.timestamp();
    this.id = instanceId;
    this.initialId = initialInstanceId;
    this.currentId = currentInstanceId;
  }

  private InstanceId id() {
    return this.id;
  }

  private InstanceId initialId() {
    return this.initialId;
  }

  private InstanceId currentId() {
    return this.currentId;
  }

  private InstanceId updateInstance() {
    this.initialId = new InstanceId(id.id());
    this.id = new InstanceId();
    this.currentId = new InstanceId(id.id());
    return id;
  }

  // @Override
  public boolean isFirst() {
    return (initialId.id().equals(id.id()));
  }

  // @Override
  public boolean isCurrent() {
    return (currentId.id().equals(id.id()));
  }

  // @Override
  public Timestamp timestamp() {
    return this.timestamp;
  }

  // @Override
  public void timestamp(Timestamp ts) {
    this.timestamp = ts;
  }

  private Timestamp stamptime() {
    this.timestamp = Util.Time.timestamp();
    return this.timestamp;
  }

  private <U extends HasStatus> boolean updateBeforeSave(U instance) {
    return (instance.states().wasLoaded()
        || (!instance.states().isNew() && (instance.states().isModified() || isFirst())));
  }

  private <U extends HasStatus> boolean notReadyToSave(U instance) {
    return (instance.states().wasLoaded()
        || (!isCurrent() && (!instance.states().isModified() || !instance.states().isNew())));
  }

  public static class Dao implements CampInstanceDaoInterface<HasResultSetToInstance<T>> {

    public static String dbName = CampSQL.database[CampSQL._ORDER_DB_INDEX];

    public static String table =
        CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL._ORDER_HISTORY_TABLE_INDEX);

    public static String itable =
        CampSQL.omTable(CampSQL._ORDER_DB_INDEX, CampSQL._ORDER_TABLE_INDEX);

    public static String[][] tabledef = CampSQL.order_history_table_definition;

    @Override
    public static String dbName() {
      return dbName;
    }

    @Override
    public String table(IsInstanceDao dao) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String[][] tabledef(IsInstanceDao dao) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String itable(IsInstanceDao dao) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Object rsToI(ResultSet rs, boolean log) throws SQLException {
      String instanceId = rs.getString("_instance_id");
      if (log && !Util._IN_PRODUCTION) {
        msg = "----[ instance id is '" + instanceId + "']----";
        LOG.info(String.format(fmt, _f, msg));
      }

      String currentInstanceId = rs.getString("_current_instance_id");
      if (log && !Util._IN_PRODUCTION) {
        msg = "----[ current instance id is '" + currentInstanceId + "']----";
        LOG.info(String.format(fmt, _f, msg));
      }

      String initialInstanceId = rs.getString("_initial_instance_id");
      if (log && !Util._IN_PRODUCTION) {
        msg = "----[ initial instance id is '" + initialInstanceId + "']----";
        LOG.info(String.format(fmt, _f, msg));
      }

      Timestamp timestamp = rs.getTimestamp("_timestamp");
      if (log && !Util._IN_PRODUCTION) {
        msg = "----[ timestamp is '" + timestamp + "']----";
        LOG.info(String.format(fmt, _f, msg));
      }

      Order.OrderStatus status = Order.OrderStatus.valueOf(rs.getString("_status"));
      if (log && !Util._IN_PRODUCTION) {
        msg = "----[ order state is '" + status.name() + "']----";
        LOG.info(String.format(fmt, _f, msg));
      }
    }
  }
}
