package com.app.employeePortal.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.api.entity.CurrencyExchangeApiData;


@Repository
public interface CurrencyExchangeApiRepository extends JpaRepository<CurrencyExchangeApiData, String>{

	
	@Query(value = "select a  from CurrencyExchangeApiData a  where a.record_date=:recordDate" )
	List<CurrencyExchangeApiData> fetchCurrencyDataByDate(@Param(value="recordDate") Date recordDate);

}
