package com.camsolute.code.camp.lib.contract.core;

import com.camsolute.code.camp.lib.utilities.Util;

public interface CampStatus extends HasCampStatusHandler {
	
	public static enum OrderLifeCycle {
		CREATED,
		SUBMITTED,
		REJECTED,
		RELEASED,
		PRODUCTION,
		UPDATED,
		SHIPPING,
		FULFILLED,
		MODIFIED,
		CLEAN;
		
		@Override
		public String toString() {
			return Util.Config.instance().description("order.lifecycle."+name().toLowerCase());
		}		
		
		public static boolean hasLifeCycleStage(String status) {
			boolean isElement = false;
			for(Enum<?> s :values()) {
				if(s.name().equals(status)){
					isElement = true;
					break;
				}
			}
			return isElement;
		}
	}
	
    public static enum DefaultStatus {
        CREATED,
        PERSISTED,
        MODIFIED,
        DIRTY,
        CLEAN;
        @Override
        public String toString() {
            return Util.Config.instance().description("deault.status."+name().toLowerCase());
        }		
		
        public static boolean hasStatus(String status) {
            boolean isElement = false;
            for(Enum<?> s :values()) {
                if(s.name().equals(status)){
                    isElement = true;
                    break;
                }
            }
            return isElement;
        }

    }
	public class CampStatusImpl implements CampStatus {
		
		private CampStatusHandler statusHandler;

		public CampStatusHandler statusHandler() {
			return statusHandler;
		}
		public void statusHandler(CampStatusHandler handler) {
			statusHandler = handler;
		}
		
		
	}
	
}
