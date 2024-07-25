package com.app.employeePortal.registration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String>{

	List<Currency> findByMandatoryIndAndOrgId(boolean b, String orgId);
	
	@Query(value = "select exp  from Currency exp  where exp.currency_id=:currencyId " )
	Currency getByCurrencyId(@Param(value="currencyId") String currencyId);

	List<Currency> findByCurrencyNameContainingAndOrgId(String currencyName, String orgId);

	List<Currency> findBySalesIndAndOrgId(boolean b, String orgId);

	List<Currency> findByinvestorIndAndOrgId(boolean b, String orgId);
	
	public List<Currency> findByOrgId(String orgId);

	 
	@Query(value = "select exp  from Currency exp  where exp.currency_id=:currencyId " )
	Optional<Currency> getCurrencyDetailsByCurrencyId(@Param(value="currencyId") String currencyId);

    Currency findByCurrencyNameAndOrgId(String asString, String orgId);
}
