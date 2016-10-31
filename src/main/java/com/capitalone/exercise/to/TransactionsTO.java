package com.capitalone.exercise.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionsTO {

	private long amount;
    private String categorization;
    private String merchant;

	@JsonProperty("is-pending")
	private String isPending;
	
	@JsonProperty("aggregation-time")
	private String aggregationTime;
	
	@JsonProperty("account-id")
	private String accountId;
    
	@JsonProperty("clear-date")
	private String clearDate;
    
	@JsonProperty("transaction-id")
	private String transactionId; 
    
	@JsonProperty("raw-merchant")
	private String rawMerchant;
    
    @JsonProperty("transaction-time")
    private String transactionTime;
    
    @JsonProperty("payee-name-only-for-testing")
    private String payeeNameForTesting;
    
    @JsonProperty("memo-only-for-testing")
    private String memoOnlyForTesting;
    
    @JsonProperty("previous-transaction-id")
    private String previousTransactionID;
    
    
 	public String getPayeeNameForTesting() {
		return payeeNameForTesting;
	}
	public void setPayeeNameForTesting(String payeeNameForTesting) {
		this.payeeNameForTesting = payeeNameForTesting;
	}
	public String getMemoOnlyForTesting() {
		return memoOnlyForTesting;
	}
	public void setMemoOnlyForTesting(String memoOnlyForTesting) {
		this.memoOnlyForTesting = memoOnlyForTesting;
	}
	public String getPreviousTransactionID() {
		return previousTransactionID;
	}
	public void setPreviousTransactionID(String previousTransactionID) {
		this.previousTransactionID = previousTransactionID;
	}
	
	public String getIsPending() {
		return isPending;
	}
	public void setIsPending(String isPending) {
		this.isPending = isPending;
	}
	public String getAggregationTime() {
		return aggregationTime;
	}
	public void setAggregationTime(String aggregationTime) {
		this.aggregationTime = aggregationTime;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRawMerchant() {
		return rawMerchant;
	}
	public void setRawMerchant(String rawMerchant) {
		this.rawMerchant = rawMerchant;
	}
	public String getCategorization() {
		return categorization;
	}
	public void setCategorization(String categorization) {
		this.categorization = categorization;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}

}
