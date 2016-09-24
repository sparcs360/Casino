package com.sparcs.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerImpl implements Customer {

	private static final Logger log = LoggerFactory.getLogger(CustomerImpl.class);

	private String nickName;
	private int chipCount;

	public CustomerImpl(String nickName, int chipCount) {
		
		super();
		
		this.nickName = nickName;
		this.chipCount = chipCount;
	}

	@Override
	public String getNickName() {

		return nickName;
	}

	@Override
	public int getChipCount() {

		return chipCount;
	}

	@Override
	public void deductChips(int stake) {

		chipCount -= stake;
		
		log.trace("{}: deducted {}c, leaving {}c", this, stake, chipCount);
	}

	@Override
	public void addChips(int pot) {

		chipCount += pot;

		log.trace("{}: adding {}c, giving {}c", this, pot, chipCount);
	}

	@Override
	public String toString() {

		return String.format("CustomerImpl@%x[%s]",
				this.hashCode(), getNickName());
	}
}
