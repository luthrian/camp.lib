package com.camsolute.code.camp.lib.contract.core;

public interface CampException extends HasLogHandler {
	
	public void printStackTrace();
	
	public abstract class AbstractCampException extends Exception implements CampException {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3536149048429166971L;
		protected LogHandler logHandler;
		
		public void printStackTrace(Class<?> clazz, String msg) {
			logHandler.logEnter(msg, "printStackTrace", clazz);
			
			super.printStackTrace();
			logHandler.logExit(msg, "printStackTrace", clazz);
		}		

		public LogHandler logHandler() {
			return logHandler;
		}

		public void logHandler(LogHandler logHandler) {
			this.logHandler = logHandler;
		}

	}

	public class DataMismatchException extends AbstractCampException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2976109144918557578L;
		
		private String msg = "";
		
		public DataMismatchException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public DataMismatchException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public DataMismatchException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public DataMismatchException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(DataMismatchException.class,msg);
		}			
	}
	
	public class PersistanceException extends AbstractCampException {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4203607244681604812L;

		private String msg = "";
		
		public PersistanceException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public PersistanceException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public PersistanceException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public PersistanceException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
 		public void printStackTrace() {
			printStackTrace(PersistanceException.class,msg);
		}
		
	}
	
	public class StatusDirtyException extends AbstractCampException {

		private static final long serialVersionUID = 4183370633967857338L;

		private String msg = "";
		
		public StatusDirtyException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public StatusDirtyException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}
		
		public StatusDirtyException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public StatusDirtyException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(StatusDirtyException.class,msg);
		}
	}
	
	public class StatusMismatchException extends AbstractCampException {

		private static final long serialVersionUID = -4666342985577080020L;

		private String msg = "";
		
		public StatusMismatchException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public StatusMismatchException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public StatusMismatchException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public StatusMismatchException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(StatusMismatchException.class,msg);
		}			
	}

	public class ElementNotInListException extends AbstractCampException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5215510675655264477L;
		private String msg = "";
		
		public ElementNotInListException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public ElementNotInListException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public ElementNotInListException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public ElementNotInListException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(ElementNotInListException.class,msg);
		}			
	}

	public class AlreadyContainsElementException extends AbstractCampException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5215510675655264477L;
		private String msg = "";
		
		public AlreadyContainsElementException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public AlreadyContainsElementException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public AlreadyContainsElementException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public AlreadyContainsElementException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(AlreadyContainsElementException.class,msg);
		}			
	}

	public class TableDimensionsException extends AbstractCampException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5215510675655264477L;
		private String msg = "";
		
		public TableDimensionsException() {
			logHandler  = new LogHandler.DefaultLogHandler();
		}
		
		public TableDimensionsException(String msg) {
			logHandler  = new LogHandler.DefaultLogHandler();
			this.msg = msg;
		}

		public TableDimensionsException(String msg,Exception e) {
			this(msg);
			setStackTrace(e.getStackTrace());
		}
		
		public TableDimensionsException(Exception e) {
			this();
			setStackTrace(e.getStackTrace());
		}
		
		public void printStackTrace() {
			printStackTrace(TableDimensionsException.class,msg);
		}			
	}


}
