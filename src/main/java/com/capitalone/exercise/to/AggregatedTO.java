package com.capitalone.exercise.to;

import java.util.List;

public class AggregatedTO {
	
	private String month;
	private FormatedTransactionsTO incomeTO;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public FormatedTransactionsTO getIncomeTO() {
		return incomeTO;
	}
	public void setIncomeTO(FormatedTransactionsTO incomeTO) {
		this.incomeTO = incomeTO;
	}

}
