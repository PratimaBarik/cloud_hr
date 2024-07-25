package com.app.employeePortal.api.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.employeePortal.api.entity.CurrencyExchange;
import com.app.employeePortal.api.entity.CurrencyExchangeApiData;
import com.app.employeePortal.api.repository.CurrencyExchangeApiRepository;
import com.app.employeePortal.api.repository.CurrencyExchangeRepository;
import com.app.employeePortal.util.Utility;


@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
	
	@Autowired
	CurrencyExchangeApiRepository currencyExchangeApiRepository;
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository ;
	
	private String currencyApiKey = "c00e4279d51967d20852";

	@Override
    @Scheduled(cron = "0 0 0 * * *")
	public void saveToCurrencyExchangeApiData() {
		
		System.out.println("scheduler method executed........");
		LocalDate date = LocalDate.now();  

		
		
			String[] currency = { "EUR", "INR", "USD", "GBP","AUD","CAD","SGD","BDT" };
			
			try {
				
				for (int i = 0; i < currency.length; i++) {

					CurrencyExchangeApiData currencyExchange = new CurrencyExchangeApiData();

					for (int j = 0; j < currency.length; j++) {
						String url = "https://free.currencyconverterapi.com/api/v6/convert?q=" + currency[i] + "_"+ currency[j] + "&compact=ultra"+"&date="+date.toString()+"&apiKey="+currencyApiKey;
						RestTemplate restTemplate = new RestTemplate();
						
						try {
							
							String response = restTemplate.getForObject(url, String.class);
								
						if(null!=response ) {
													
						JSONObject jsonObject = new JSONObject(response); 
						
						if (jsonObject != null) {
														
							
                            JSONObject jsonObject2 = jsonObject.getJSONObject(currency[i] + "_" + currency[j]);
							
							double amount = jsonObject2.getDouble(date.toString());
							
							currencyExchange.setBase(currency[i].toString());
							currencyExchange.setRecord_date(Utility.removeTime(Utility.getUtilDateByLocalDate(date)));
							if (currency[j].equals("EUR"))
								currencyExchange.setEUR(amount);

							if (currency[j].equals("INR"))
								currencyExchange.setINR(amount);

							if (currency[j].equals("USD"))
								currencyExchange.setUSD(amount);

							if (currency[j].equals("GBP"))
								currencyExchange.setGBP(amount);


						    if (currency[j].equals("AUD"))
							currencyExchange.setAUD(amount);
						    
                            if (currency[j].equals("CAD"))
							currencyExchange.setCAD(amount);

						

						  if (currency[j].equals("SGD"))
							currencyExchange.setSGD(amount);
						  
						  if (currency[j].equals("BDT"))
								currencyExchange.setBDT(amount);
						  
						}

						}
					} catch (Exception e) {
						
						
						
						e.printStackTrace();
					}
				}
					
					CurrencyExchangeApiData	currencyExchangeApiData =currencyExchangeApiRepository.save(currencyExchange);
					
					System.out.println("data inserted to api table.........." +currencyExchangeApiData);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		
	
	
	}
	

	@Override
    @Scheduled(cron = " 0 00 02 * * *")

	public void saveToCurrencyExchange() {

		
		
		List<CurrencyExchangeApiData> list = currencyExchangeApiRepository.fetchCurrencyDataByDate(Utility.getUtilDateByLocalDate(LocalDate.now()));
		
		if(null!=list && !list.isEmpty()) {
			
			for (CurrencyExchangeApiData currencyExchangeApiTable : list) {
				CurrencyExchange currencyEx = new CurrencyExchange();
				
			
				if(null!=currencyExchangeApiTable.getRecord_date() && null!=currencyExchangeApiTable.getBase()
						&& currencyExchangeApiTable.getEUR()!=0.0000 && currencyExchangeApiTable.getGBP()!=0.0000
						&& currencyExchangeApiTable.getINR()!=0.0000 && currencyExchangeApiTable.getUSD()!=0.0000
						&& currencyExchangeApiTable.getAUD()!=0.0000 && currencyExchangeApiTable.getCAD()!=0.0000
						&& currencyExchangeApiTable.getSGD()!=0.0000
						&& currencyExchangeApiTable.getBDT()!=0.0000) {
					
								
					System.out.println("insert to eur");
					System.out.println("insert bdt");
					System.out.println("gbp");
					
					currencyEx.setBase(currencyExchangeApiTable.getBase());
					
					
					currencyEx.setRecordDate(Utility.removeTime(Utility.getUtilDateByLocalDate(LocalDate.now())));
					currencyEx.setEUR(currencyExchangeApiTable.getEUR());
					currencyEx.setINR(currencyExchangeApiTable.getINR());
					currencyEx.setUSD(currencyExchangeApiTable.getUSD());
					currencyEx.setGBP(currencyExchangeApiTable.getGBP());	
					currencyEx.setAUD(currencyExchangeApiTable.getAUD());
					currencyEx.setCAD(currencyExchangeApiTable.getCAD());
					currencyEx.setSGD(currencyExchangeApiTable.getSGD());
					currencyEx.setBDT(currencyExchangeApiTable.getBDT());
					currencyExchangeRepository.save(currencyEx);
				}
				
			}
			
			
		}

			
	}
	
	@Override
	public double getExchangePrice(String baseCurrency, String exchangeCurrency, double amount, Date exchangeDate) {

		
		double exchangeRate = 0 ;
		double exchangeAmount = 0 ;
		
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.getExchangeRate(baseCurrency, exchangeDate);
		
		
		if (currencyExchange != null ) {
			 
			
			if (exchangeCurrency.equalsIgnoreCase("EUR")) {				
				exchangeRate = currencyExchange.getEUR();			
			} 
			else if (exchangeCurrency.equalsIgnoreCase("GBP")) {
         
				exchangeRate = currencyExchange.getGBP();		
			}
			else if (exchangeCurrency.equalsIgnoreCase("INR")) {
				exchangeRate = currencyExchange.getINR();
			}
			else if (exchangeCurrency.equalsIgnoreCase("USD")) {
				exchangeRate = currencyExchange.getUSD();
			}
			else if (exchangeCurrency.equalsIgnoreCase("AUD")) {
				exchangeRate = currencyExchange.getAUD();
			}
			else if (exchangeCurrency.equalsIgnoreCase("CAD")) {
				exchangeRate = currencyExchange.getCAD();
			}
			else if (exchangeCurrency.equalsIgnoreCase("SGD")) {
				exchangeRate = currencyExchange.getSGD();
			}else if (exchangeCurrency.equalsIgnoreCase("BDT")) {
				exchangeRate = currencyExchange.getBDT();
			}

			exchangeAmount = exchangeRate*amount ;	
			
			System.out.println("exchange Amountttt"+exchangeAmount);			
		}
		
		return exchangeAmount;
	}

}
