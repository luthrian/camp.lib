package com.camsolute.code.camp.lib.models.customer;

import com.camsolute.code.camp.lib.dao.DaoInterface;
import com.camsolute.code.camp.lib.dao.HasProcessReference;
import com.camsolute.code.camp.lib.dao.InstanceDaoInterface;
import com.camsolute.code.camp.lib.dao.database.DBDaoInterface;
import com.camsolute.code.camp.lib.models.order.Order;

public interface CustomerDaoInterface extends DaoInterface<Customer>, InstanceDaoInterface<Customer>, DBDaoInterface<Order>, HasProcessReference<Order>{

}
