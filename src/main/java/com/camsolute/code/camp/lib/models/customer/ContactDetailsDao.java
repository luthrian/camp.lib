package com.camsolute.code.camp.lib.models.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDetailsDao implements ContactDetailsDaoInterface, ContactDetailsDBDaoInterface {

	@Override
	public String dbName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String table() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] tabledef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatestable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] updatestabledef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertValues(ContactDetails c, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertValues(ContactDetailsList cl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateValues(ContactDetails c, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUpdateValues(ContactDetailsList cl, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails rsToI(ResultSet rs, boolean log) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTable(boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clearTables(boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ContactDetails loadById(int id, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadByEmail(String emailAddress, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadBySkype(String mobileNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByPhone(String phoneNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByMobile(String mobileNumber, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadByMisc(String misc, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails create(String email, String mobile, String telephone, String skype, String misc, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails save(ContactDetails c, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList saveList(ContactDetailsList cl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails update(ContactDetails c, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList updateList(ContactDetailsList cl, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetailsList loadUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDetails loadUpdate(String customerBusinessid, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addToUpdates(String customerBusinessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addListToUpdates(CustomerList customerList, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllFromUpdates(String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByKey(String businessKey, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdatesByTarget(String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteFromUpdates(String customerBusinessId, String businessKey, String target, boolean log) {
		// TODO Auto-generated method stub
		return 0;
	}

}
