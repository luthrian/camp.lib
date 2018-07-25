package com.camsolute.code.camp.lib.contract.value;

import com.camsolute.code.camp.lib.contract.core.CampSQL;
import com.camsolute.code.camp.lib.contract.db.MethodParameter;

public interface ValueMethodParameter extends MethodParameter<Value<?,?>> {
	
	public class ByIdValueMethodParameter implements ValueMethodParameter {
		public String whereClause(String v1) {
			return " `"+CampSQL.System.valueTableDefinition.id+"`='"+v1+"'";
		}
	}

	public class ByObjectIdValueMethodParameter implements ValueMethodParameter {
		public String whereClause(String value) {
			return " `"+CampSQL.System.valueTableDefinition.objectId+"`='"+value+"'";
		}
	}

	public class ByParentIdValueMethodParameter implements ValueMethodParameter {
		public String whereClause(String value) {
			return " `"+CampSQL.System.valueTableDefinition.parentId+"`='"+value+"'";
		}
	}
}
