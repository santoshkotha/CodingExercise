package com.capitalone.exercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.capitalone.exercise.to.AggregatedTO;
import com.capitalone.exercise.to.FormatedTransactionsTO;
import com.capitalone.exercise.to.IncomeTO;
import com.capitalone.exercise.to.ResponseTO;
import com.capitalone.exercise.to.TransactionsTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Hello world!
 *
 */
public class ExpenseCalculator 
{
    
	public ResponseTO getTransactions(){
		ResponseTO responseTo = new ResponseTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Client client = Client.create();

			WebResource webResource = client
			   .resource("https://2016.api.levelmoney.com/api/v2/core/get-all-transactions");
			String input = "{\"args\": {\"uid\":  1110590645, \"token\":  \"0FC5665B0B9B4CAD3B53DF96221E5A87\", \"api-token\":  \"AppTokenForInterview\", \"json-strict-mode\": false, \"json-verbose-response\": false}}";
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
			String output = response.getEntity(String.class);
			responseTo = mapper.readValue(output, ResponseTO.class);
		  }  catch (Exception e) {
			  e.printStackTrace();
		  }
		return responseTo;
	}
	
	public Map<String, IncomeTO> getMontlyExpenses(ResponseTO Transactions, int option){
		Map<String, IncomeTO> monthlyExpenses = new LinkedHashMap<String, IncomeTO>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		long totalSpent;
		for(TransactionsTO transaction : Transactions.getTransactions()){
	    	IncomeTO income;
	    	String yearMonth=null;
	    	try {
		         Date date = formatter.parse(transaction.getTransactionTime().replaceAll(".000Z$", "+0000"));
		         Calendar cal = Calendar.getInstance();
		         cal.setTime(date);
		         int year = cal.get(Calendar.YEAR);
		         int month = cal.get(Calendar.MONTH) + 1;
		         yearMonth = ""+year+"_"+month;
	    	} catch (ParseException e) {
	            e.printStackTrace();
	    	}     
		    if(!monthlyExpenses.containsKey(yearMonth)){
		        income = new IncomeTO();
		        if(transaction.getAmount() >= 0){
		        	if(option == 2) {
		        		if(transaction.getRawMerchant().contains("CREDIT CARD PAYMENT")) {
		        			
		        		} else {
		        			income.setIncome(transaction.getAmount());
		        		}
		        	} else {
		        		income.setIncome(transaction.getAmount());
		        	}
		        }else {
		        	if(option==3){
		        		if(transaction.getRawMerchant().contains("DUNKIN") || transaction.getRawMerchant().contains("Krispy")){
		        			
		        		} else {
		        			income.setSpent(transaction.getAmount());
		        		}
		        	}else if(option == 2) {
		        		if(transaction.getRawMerchant().contains("CC PAYMENT")) {
		        			
		        		} else {
		        			income.setSpent(transaction.getAmount());
		        		}
		        	} else {
		        		income.setSpent(transaction.getAmount());
		        	}        	
		        }	 
		        monthlyExpenses.put(yearMonth,income);
		    } else {
		        income = monthlyExpenses.get(yearMonth);
		        totalSpent = 0;
		        if(transaction.getAmount() >= 0){
		        	if(option == 2) {
		        		if(transaction.getRawMerchant().contains("CREDIT CARD PAYMENT")) {
		        			
		        		} else {
				        	long totalIncome = transaction.getAmount() + income.getIncome();
				        	income.setIncome(totalIncome);
		        		}
		        	} else {
			        	long totalIncome = transaction.getAmount() + income.getIncome();
			        	income.setIncome(totalIncome);
		        	}
		        }
		        else {
		        	if(option==3){
		        		if(transaction.getRawMerchant().contains("DUNKIN") || transaction.getRawMerchant().contains("Krispy")){
		        			
		        		} else {
		        			totalSpent = transaction.getAmount() + income.getSpent(); 
		        		}
		        	} else if(option == 2){
		        		if(transaction.getRawMerchant().contains("CC PAYMENT")) {
		        			
		        		} else {
		        			totalSpent = transaction.getAmount() + income.getSpent();
		        		}
		        	} else {
		        		totalSpent = transaction.getAmount() + income.getSpent();
		        	}
		        	income.setSpent(totalSpent);
		        }
		        monthlyExpenses.put(yearMonth,income);      	 
		    }
	    }
	    return monthlyExpenses;
	}
	
	public List<AggregatedTO> getAvgofTransactions(Map<String, IncomeTO> monthlyExpenses){
	    long avgIncome = 0;
	    long avgSpent = 0;
		List<AggregatedTO> aggregatedTO = new ArrayList<AggregatedTO>();
		
	    FormatedTransactionsTO transactionsTO;
	    for(String key : monthlyExpenses.keySet()){
	    	AggregatedTO aggregated = new AggregatedTO();
	    	transactionsTO = new FormatedTransactionsTO();
	    	//System.out.println("year_month"+key+"||spent"+monthlyExpenses.get(key).getSpent()+"||Income"+monthlyExpenses.get(key).getIncome());
	    	transactionsTO.setIncome("$"+monthlyExpenses.get(key).getIncome());
	    	transactionsTO.setSpent("$"+Math.abs(monthlyExpenses.get(key).getSpent()));
	    	avgIncome = avgIncome + monthlyExpenses.get(key).getIncome();
	    	avgSpent = avgSpent + monthlyExpenses.get(key).getSpent();
	    	aggregated.setMonth(key);
	    	aggregated.setIncomeTO(transactionsTO);
	    	aggregatedTO.add(aggregated);
	    }
	    AggregatedTO aggregated = new AggregatedTO();
	    transactionsTO = new FormatedTransactionsTO();
	    int transactions = monthlyExpenses.size();
	    avgIncome = avgIncome/transactions;
	    avgSpent = avgSpent/transactions;
    	
	    transactionsTO.setIncome("$"+avgIncome);
    	transactionsTO.setSpent("$"+Math.abs(avgSpent));
    	aggregated.setMonth("Average");
    	aggregated.setIncomeTO(transactionsTO);
    	aggregatedTO.add(aggregated);
	    return aggregatedTO;
	}
	
	public static void main( String[] args )
    {
		ExpenseCalculator expenseCalc = new ExpenseCalculator();
		Map<String, IncomeTO> monthlyExpenses = new LinkedHashMap<String, IncomeTO>();
		List<AggregatedTO> aggregated = new ArrayList<AggregatedTO>();
		ResponseTO response = expenseCalc.getTransactions();
		System.out.println("Hello..! Welcome to Expense Calculator. \n Please select any one option from Below to proceed Ahead");
		System.out.println("1. Select 1 for all Tranactions");
		System.out.println("2. Select 2 to Ignore Credit card Transactions");
		System.out.println("3. Select 3 to Ignore Donut Transactions");
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		int n = reader.nextInt(); 
		if(response!=null){
			switch(n){
			case 1 : 
					 monthlyExpenses = expenseCalc.getMontlyExpenses(response,1);	
						break;
			case 2 : 
					 monthlyExpenses = expenseCalc.getMontlyExpenses(response,2);	
						break;
			case 3 : 
					 monthlyExpenses = expenseCalc.getMontlyExpenses(response,3);
					 	break;
			default : System.out.println("Not a Valid Input!");
						break;
			}
			
			aggregated = expenseCalc.getAvgofTransactions(monthlyExpenses);
			for(AggregatedTO aggregatedTO : aggregated){
				System.out.println("Month:"+aggregatedTO.getMonth()+"- Spent:"+aggregatedTO.getIncomeTO().getSpent()+"-Income:"+aggregatedTO.getIncomeTO().getIncome());
			}
		} else {
			System.out.println("No Transactions Found..!!!");
		}
		
    }
}
