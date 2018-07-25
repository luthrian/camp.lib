package com.camsolute.code.camp.lib.contract.core;

import com.camsolute.code.camp.lib.contract.core.CampException.StatusDirtyException;
import com.camsolute.code.camp.lib.contract.core.CampException.StatusMismatchException;
import com.camsolute.code.camp.lib.contract.core.CampStatus.OrderLifeCycle;

public interface CampStatusHandler {

	public Enum<?> status();

	/**
	 * Request that the business object update a process relevant status aspect.
	 * @param bo the business object <code>IsBusinessObject</code> contract
	 * @param newStatus the new process relevant status aspect value.
	 * @param registerUpdate a boolean flag indicating if the update should be registered in the states aspect via the <code>IsBusinessObject</code> contract. 
	 * @return the value of the previous process relevant status aspect.
	 * @throws StatusMismatchException exception thrown if the <codE>newStatus<code> parameter value declaring class differs from the CampStatus handlers status aspect
	 */
  public Enum<?> updateStatus(IsBusinessObject bo, Enum<?> newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException;

  public Enum<?> updateStatus(IsBusinessObject bo, String newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException;

  public Enum<?> previousStatus();

  public void setPreviousStatus(Enum<?> newStatus) throws StatusMismatchException, StatusDirtyException;

  public void setPreviousStatus(String newStatus) throws StatusMismatchException, StatusDirtyException;
  
  public void cleanStatus(IsBusinessObject object) throws StatusMismatchException, StatusDirtyException;


  public class OrderLifeCycleHandler implements CampStatusHandler {

  	private OrderLifeCycle status = OrderLifeCycle.CREATED;
  	private OrderLifeCycle previousStatus = OrderLifeCycle.CLEAN;

  	private void mismatchCheck(Enum<?> newStatus, OrderLifeCycle oldStatus, String exceptionMessage) throws StatusMismatchException, StatusDirtyException {
			if(newStatus.getDeclaringClass() != oldStatus.getDeclaringClass()) {
				throw new StatusMismatchException(exceptionMessage);
			}
			checkForUnhandledUpdate();
  	}
  	private void mismatchCheck(String newStatus, String exceptionMessage) throws StatusMismatchException, StatusDirtyException {
			if(!OrderLifeCycle.hasLifeCycleStage(newStatus)) {
				throw new StatusMismatchException(exceptionMessage);
			}
			checkForUnhandledUpdate();
  	}
  	private void checkForUnhandledUpdate() throws StatusDirtyException {
			if(!previousStatus.equals(OrderLifeCycle.MODIFIED)||!previousStatus.equals(OrderLifeCycle.CLEAN) ) {
				throw new StatusDirtyException("Attempting to update an status value before a prior status update has been handled by the system.");
			}
  	}
  	
 		public Enum<?> status() {
			return status;
		}

		public Enum<?> updateStatus(IsBusinessObject bo, Enum<?> newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,status,"Expected newStatus declaring class to be ("+status.getDeclaringClass().getSimpleName()+") but got ("+newStatus.getDeclaringClass().getSimpleName()+")");
			previousStatus = status;
			status = (OrderLifeCycle)newStatus;
			if(registerUpdate) {
				bo.states().modify();
			}
			return previousStatus;
		}

		public Enum<?> updateStatus(IsBusinessObject bo, String newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,"newStatus("+newStatus+") is not a OrderLifeCycle stage/status");
			previousStatus = status;
			status = OrderLifeCycle.valueOf(newStatus);
			if(registerUpdate) {
				bo.states().modify();
			}
			return previousStatus;
		}

		public Enum<?> previousStatus() {
			return previousStatus;
		}

		public void setPreviousStatus(Enum<?> newStatus) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,previousStatus,"Expected newStatus declaring class to be ("+previousStatus.getDeclaringClass().getSimpleName()+") but got ("+newStatus.getDeclaringClass().getSimpleName()+")");
			previousStatus = (OrderLifeCycle)newStatus;
		}

		public void setPreviousStatus(String newStatus) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,"newStatus("+newStatus+") is not a OrderLifeCycle stage/status");
			previousStatus = OrderLifeCycle.valueOf(newStatus);
		}

 		public void cleanStatus(IsBusinessObject object) throws StatusMismatchException, StatusDirtyException {
 			
 			if(!((OrderLifeCycle)object.status().statusHandler().previousStatus()).equals(OrderLifeCycle.CLEAN)) {
 			
 				object.status().statusHandler().updateStatus(object,(OrderLifeCycle)object.status().statusHandler().previousStatus(),false);
 				
 				object.status().statusHandler().setPreviousStatus(OrderLifeCycle.CLEAN);
 			
 			}
		}
  }
  public class DefaultStatusHandler implements CampStatusHandler {

  	private OrderLifeCycle status = OrderLifeCycle.CREATED;
  	private OrderLifeCycle previousStatus = OrderLifeCycle.CLEAN;

  	private void mismatchCheck(Enum<?> newStatus, OrderLifeCycle oldStatus, String exceptionMessage) throws StatusMismatchException, StatusDirtyException {
			if(newStatus.getDeclaringClass() != oldStatus.getDeclaringClass()) {
				throw new StatusMismatchException(exceptionMessage);
			}
			checkForUnhandledUpdate();
  	}
  	private void mismatchCheck(String newStatus, String exceptionMessage) throws StatusMismatchException, StatusDirtyException {
			if(!OrderLifeCycle.hasLifeCycleStage(newStatus)) {
				throw new StatusMismatchException(exceptionMessage);
			}
			checkForUnhandledUpdate();
  	}
  	private void checkForUnhandledUpdate() throws StatusDirtyException {
			if(!previousStatus.equals(OrderLifeCycle.MODIFIED)||!previousStatus.equals(OrderLifeCycle.CLEAN) ) {
				throw new StatusDirtyException("Attempting to update an status value before a prior status update has been handled by the system.");
			}
  	}
  	
 		public Enum<?> status() {
			return status;
		}

		public Enum<?> updateStatus(IsBusinessObject bo, Enum<?> newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,status,"Expected newStatus declaring class to be ("+status.getDeclaringClass().getSimpleName()+") but got ("+newStatus.getDeclaringClass().getSimpleName()+")");
			previousStatus = status;
			status = (OrderLifeCycle)newStatus;
			if(registerUpdate) {
				bo.states().modify();
			}
			return previousStatus;
		}

		public Enum<?> updateStatus(IsBusinessObject bo, String newStatus, boolean registerUpdate) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,"newStatus("+newStatus+") is not a OrderLifeCycle stage/status");
			previousStatus = status;
			status = OrderLifeCycle.valueOf(newStatus);
			if(registerUpdate) {
				bo.states().modify();
			}
			return previousStatus;
		}

		public Enum<?> previousStatus() {
			return previousStatus;
		}

		public void setPreviousStatus(Enum<?> newStatus) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,previousStatus,"Expected newStatus declaring class to be ("+previousStatus.getDeclaringClass().getSimpleName()+") but got ("+newStatus.getDeclaringClass().getSimpleName()+")");
			previousStatus = (OrderLifeCycle)newStatus;
		}

		public void setPreviousStatus(String newStatus) throws StatusMismatchException, StatusDirtyException {
			mismatchCheck(newStatus,"newStatus("+newStatus+") is not a OrderLifeCycle stage/status");
			previousStatus = OrderLifeCycle.valueOf(newStatus);
		}

 		public void cleanStatus(IsBusinessObject object) throws StatusMismatchException, StatusDirtyException {
 			
 			if(!((OrderLifeCycle)object.status().statusHandler().previousStatus()).equals(OrderLifeCycle.CLEAN)) {
 			
 				object.status().statusHandler().updateStatus(object,(OrderLifeCycle)object.status().statusHandler().previousStatus(),false);
 				
 				object.status().statusHandler().setPreviousStatus(OrderLifeCycle.CLEAN);
 			
 			}
		}
  }
}
