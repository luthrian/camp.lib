package com.camsolute.code.camp.lib.contract.value;

public interface HasSQLValueHandler <T,Q extends Value<T,Q>> {
	public <S extends ValuePersistHandler<T,Q>> S sqlHandler();
	public <S extends ValuePersistHandler<T,Q>> void sqlHandler(S sqlValueHandler);
}
