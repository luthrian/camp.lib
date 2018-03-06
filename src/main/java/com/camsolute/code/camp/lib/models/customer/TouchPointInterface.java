package com.camsolute.code.camp.lib.models.customer;

import java.sql.Timestamp;

import com.camsolute.code.camp.lib.contract.HasDate;
import com.camsolute.code.camp.lib.contract.IsObjectInstance;

public interface TouchPointInterface extends HasDate, IsObjectInstance<TouchPoint> {

	public String businessKeyResponsible();

	public void setBusinessKeyResponsible(String businessKeyResponsible);

	public String updateBusinessKeyResponsible(String businessKeyResponsible);

	public String businessIdResponsible();

	public void setBusinessIdResponsible(String businessIdResponsible);

	public String updateBusinessIdResponsible(String businessIdResponsible);

	public String businessKeyCustomer();

	public void setBusinessKeyCustomer(String businessKeyCustomer);

	public String updateBusinessKeyCustomer(String businessKeyCustomer);

	public String businessIdCustomer();
	
	public void setBusinessIdCustomer(String businessIdCustomer);
	
	public String updateBusinessIdCustomer(String businessIdCustomer);

	public Timestamp nextDate();
	
	public Timestamp updateNextDate(Timestamp nextDate);
	
	public void setNextDate(Timestamp nextDate);
	
	public String minutes();
	
	public String updateMinutes(String topic);
	
	public void setMinutes(String topic);
	
}
