package com.sparcs.casino;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.Bet;

@Component
public class BankImpl implements Bank {

	private static final Logger log = LoggerFactory.getLogger(BankImpl.class);
	
	@PostConstruct
	private void postConstruct() {

		log.trace("Created {}", this);
	}

	@Override
	public int getChipCount(Customer customer) {

		log.trace("{}: getChipCount(customer={})", this, customer);

		Account account = Bank.ACCOUNTS.get(customer);
		if( account == null ) {
			log.error("No Account for Customer {}", customer);
			// PANIC
			return -1;
		}
		
		return account.getChipCount();
	}
	
	@Override
	public void processBet(Bet bet) {

		log.trace("{}: processBet(bet={})", this, bet);
		
		Customer customer = bet.getPlayer().getCustomer();
		Account account = Bank.ACCOUNTS.get(customer);
		if( account == null ) {
			log.error("No Account for Customer {}", customer);
			// PANIC
			return;
		}
		
		account.deductChips(bet.getStake());
	}

	@Override
	public void processWinnings(Bet bet, int winnings) {

		log.trace("{}: processWinnings(bet={}, winnings={})", this, bet, winnings);

		Customer customer = bet.getPlayer().getCustomer();
		Account account = Bank.ACCOUNTS.get(customer);
		if( account == null ) {
			log.error("No Account for Customer {}", customer);
			// PANIC
			return;
		}
		
		account.addChips(winnings);
	}

	@Override
	public String toString() {

		return String.format("%s@%x",
				getClass().getSimpleName(), hashCode());
	}
}
