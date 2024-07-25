package com.app.employeePortal.api.service;

import java.util.Date;

public interface CurrencyExchangeService {
	
	public void saveToCurrencyExchangeApiData();
	
	public void saveToCurrencyExchange();

	public double getExchangePrice(String baseCurrency, String exchangeCurrency, double amount, Date exchangeDate);
	
	
}
