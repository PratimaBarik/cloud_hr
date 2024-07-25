
package com.app.employeePortal.api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.api.entity.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, String>{

	
	@Query(value = "select c from CurrencyExchange c  where c.base=:base and c.recordDate=:exchangeDate" )
	public CurrencyExchange getExchangeRate(@Param(value="base")String baseCurrency,@Param(value="exchangeDate") Date exchangeDate);

}

