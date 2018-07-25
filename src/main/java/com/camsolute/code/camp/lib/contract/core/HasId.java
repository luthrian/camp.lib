package com.camsolute.code.camp.lib.contract.core;

import java.util.UUID;

public interface HasId {
	public static String newId() {
		return UUID.randomUUID().toString();
	}
	public String id();
	public String updateId(String id);
}
