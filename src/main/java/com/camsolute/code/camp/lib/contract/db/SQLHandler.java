package com.camsolute.code.camp.lib.contract.db;

public interface SQLHandler<T> extends SQLCreateHandler<T>, SQLReadHandler<T>, SQLUpdateHandler<T>, SQLDeleteHandler<T> {

}
