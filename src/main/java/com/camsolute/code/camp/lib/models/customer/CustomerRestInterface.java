package com.camsolute.code.camp.lib.models.customer;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.rest.CampInstanceRestDaoInterface;

public interface CustomerRestInterface  extends HasProcessReference, DaoInterface<Customer>, CampInstanceRestDaoInterface {

}
