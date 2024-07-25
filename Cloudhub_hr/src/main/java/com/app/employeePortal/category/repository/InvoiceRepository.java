package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

	List<Invoice> findByOrgId(String orgId);

	List<Invoice> findByUserId(String userId);
	
	@Query(value = "select a  from Invoice a  where a.projectId=:projectName and a.customerId=:customerId and a.month=:month and a.year=:year " )
	public List<Invoice> getCandidateByProjectIdandCustomerId(@Param(value="projectName")String projectName, 
			@Param(value="customerId")String customerId, @Param(value="month")String month, @Param(value="year")String year);
	

}
