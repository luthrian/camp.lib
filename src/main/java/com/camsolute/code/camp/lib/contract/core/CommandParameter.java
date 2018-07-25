package com.camsolute.code.camp.lib.contract.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommandParameter extends Map<String,Object> {
	public class CommandParameterImpl extends HashMap<String,Object> implements CommandParameter {
		private static final long serialVersionUID = 6388282838337882330L;
	}
}
