package com.camsolute.code.camp.lib.contract.history;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.history.JSONHistoryHandler.	JSONHistoryHandlerImpl;
import com.camsolute.code.camp.lib.contract.history.SQLHistoryHandler.MysqlSQLHistoryHandler;
import com.camsolute.code.camp.lib.contract.core.CampData;
import com.camsolute.code.camp.lib.models.InstanceId;
import com.camsolute.code.camp.lib.utilities.Util;

public interface HistoryMarker extends HasJSONHistoryHandler, HasSQLHistoryHandler {

  public int objectReferenceId();
  public void objectReferenceId(int referenceId);

  public InstanceId id();
  public InstanceId initialId();
  public InstanceId currentId();

  public void updateInstance(String id, String currentId, String initialId);

  public InstanceId updateHistoryMarker();

  public boolean isFirst();
  public boolean isCurrent();

  public Timestamp timestamp();
  public Timestamp creationDate();
  public Timestamp endOfLife();

  public void timestamp(Timestamp timestamp);
  public void creationDate(Timestamp timestamp);
  public void endOfLife(Timestamp timestamp);

  public void stamptime();

  public HistoryMarker clone();

   public static HistoryMarker clone(HistoryMarker instance) {
      HistoryMarker h = new DefaultHistoryMarker(instance.id(),instance.currentId(),instance.initialId());
      h.objectReferenceId(instance.objectReferenceId());
      h.creationDate(instance.creationDate());
      h.timestamp(instance.timestamp());
      h.endOfLife(instance.endOfLife());
      return h;
    }

   public class DefaultHistoryMarker implements HistoryMarker {

     private InstanceId id;
     private InstanceId initialId;
     private InstanceId currentId;

     private int objectReferenceId = CampData.NEW_ID;

     private Timestamp timestamp;
     private Timestamp creationDate;
     private Timestamp endOfLife;

     private JSONHistoryHandler jsonHandler;
     private SQLHistoryHandler sqlHandler;

     public DefaultHistoryMarker() {
       this.id = new InstanceId();
       this.currentId = new InstanceId(id.id());
       this.initialId = new InstanceId(id.id());
       this.timestamp = Util.Time.timestamp();
       this.creationDate = this.timestamp;
       this.endOfLife = Util.Time.timestamp(Util.Time.datePlus(365,this.timestamp,Util.Time.formatMilli));
     }

     public DefaultHistoryMarker(InstanceId id, InstanceId currentId, InstanceId initialId) {
       this.id = id;
       this.initialId = initialId;
       this.currentId = currentId;
       this.jsonHandler = new JSONHistoryHandlerImpl();
       this.sqlHandler = new MysqlSQLHistoryHandler();
     }

     public DefaultHistoryMarker(String id, String currentId, String initialId) {
       this.id = new InstanceId(id);
       this.initialId = new InstanceId(initialId);
       this.currentId = new InstanceId(currentId);
       this.jsonHandler = new JSONHistoryHandlerImpl();
       this.sqlHandler = new MysqlSQLHistoryHandler();
     }

    public JSONHistoryHandler jsonHandler() {
      return jsonHandler;
    }

    public void jsonHandler(JSONHistoryHandler handler) {
      jsonHandler = handler;
    }

    public SQLHistoryHandler sqlHistoryHandler() {
      return sqlHandler;
    }

    public void sqlHistoryHandler(SQLHistoryHandler handler) {
      sqlHandler = handler;
    }

    public int objectReferenceId() {
      return objectReferenceId;
    }

    public void objectReferenceId(int referenceId) {
      objectReferenceId = referenceId;
    }

    public InstanceId id() {
      return id;
    }

    public InstanceId initialId() {
      return initialId;
    }

    public InstanceId currentId() {
      return currentId;
    }

    public InstanceId updateHistoryMarker() {
      this.initialId = new InstanceId(id.id());
      this.id = new InstanceId();
      this.currentId = new InstanceId(id.id());
      return this.id;
    }

    public void updateInstance(String id, String currentId, String initialId) {
      this.id = new InstanceId(id);
      this.currentId = new InstanceId(currentId);
      this.initialId = new InstanceId(initialId);
    }

    public boolean isFirst() {
      return (initialId.id().equals(id.id()));
    }

    public boolean isCurrent() {
      return (currentId.id().equals(id.id()));
    }

    public Timestamp timestamp() {
      return timestamp;
    }

    public Timestamp creationDate() {
      return creationDate;
    }

    public Timestamp endOfLife() {
      return endOfLife;
    }

    public void timestamp(Timestamp timestamp) {
      this.timestamp = timestamp;
    }

    public void creationDate(Timestamp timestamp) {
      this.creationDate = timestamp;
    }

    public void endOfLife(Timestamp timestamp) {
      this.endOfLife = timestamp;
    }

    public void stamptime() {
      this.timestamp = Util.Time.timestamp();
    }

    public HistoryMarker clone() {
      return HistoryMarker.clone(this);
    }

   }
}
