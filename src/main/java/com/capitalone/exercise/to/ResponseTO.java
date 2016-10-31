package com.capitalone.exercise.to;

import java.util.List;

public class ResponseTO {
	
	private String error;
	private List<TransactionsTO> transactions;
	
	public List<TransactionsTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionsTO> transactions) {
		this.transactions = transactions;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
