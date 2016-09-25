package com.sparcs.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerImpl implements Customer {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);

	private String nickName;

	public CustomerImpl(String nickName) {
		
		super();
		
		this.nickName = nickName;
	}

	@Override
	public String getNickName() {

		return nickName;
	}

	@Override
	public int getChipCount() {

		return Bank.ACCOUNTS.get(this).getChipCount();
	}
	
	@Override
	public String toString() {

		return String.format("CustomerImpl@%x[%s]",
				this.hashCode(), getNickName());
	}
}
