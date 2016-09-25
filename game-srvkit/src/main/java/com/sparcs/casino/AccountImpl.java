package com.sparcs.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountImpl implements Account {

	private static final Logger log = LoggerFactory.getLogger(AccountImpl.class);

	private Customer customer;
	private int chipCount;

	public AccountImpl(Customer customer, int chipCount) {
		
		this.customer = customer;
		this.chipCount = chipCount;
	}

	@Override
	public Customer getCustomer() {

		return customer;
	}

	@Override
	public int getChipCount() {

		return chipCount;
	}
	
	@Override
	public void addChips(int pot) {

		chipCount += pot;

		log.trace("{}: adding {}c, giving {}c", this, pot, chipCount);
	}

	@Override
	public void deductChips(int stake) {

		chipCount -= stake;
		
		log.trace("{}: deducted {}c, leaving {}c", this, stake, chipCount);
	}
	
	@Override
	public String toString() {

		return String.format("%s@%x[%s=%dc]",
				getClass().getSimpleName(), hashCode(),
				customer.getNickName(), chipCount);
	}
}
