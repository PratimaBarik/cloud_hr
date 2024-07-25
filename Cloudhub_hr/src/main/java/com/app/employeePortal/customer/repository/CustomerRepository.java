package com.app.employeePortal.customer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	@Query(value = "select exp  from Customer exp  where exp.customerId=:customerId and exp.liveInd=true")
	public Customer getCustomerDetailsByCustomerId(@Param(value = "customerId") String customerId);

	@Query(value = "select exp  from Customer exp  where exp.organizationId = :orgId  and exp.liveInd=true")
	public Page<Customer> getCustomerDetailsListByOrgId(@Param(value = "orgId") String orgId, Pageable paging);

	@Query(value = "select exp  from Customer exp  where exp.organizationId = :orgId and exp.liveInd=true")
	public List<Customer> findByOrgIdandLiveInd(@Param(value = "orgId") String orgId);

	@Query(value = "select exp  from Customer exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.liveInd=true")
	public Page<Customer> getCustomerDetailsListByUserId(@Param(value = "userId") String userId, Pageable paging);

	@Query("SELECT a FROM Customer a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.liveInd = true ")
	public Page<Customer> getTeamCustomerDetailsListByUserId(@Param(value = "userIds") List<String> userIds,
			Pageable paging);

	@Query(value = "select a  from Customer a  where a.customerId=:customerId and a.liveInd=true")
	public Customer getCustomerByIdAndLiveInd(@Param(value = "customerId") String customerId);

	// public List<Customer> findByActive(boolean active);
	@Query(value = "select exp  from Customer exp  where exp.userId=:userId and exp.liveInd=true")
	public List<Customer> findByUserIdandLiveInd(@Param(value = "userId") String userId);

	@Query(value = "select exp  from Customer exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and exp.liveInd=true")
	public List<Customer> findByUserIdWithAssignedToandLiveInd(@Param(value = "userId") String userId);

	// @Query(value = "select a from Customer a where a.liveInd=true" )
	public List<Customer> findByliveInd(boolean liveInd);

	@Query(value = "select exp  from Customer exp  where exp.name=:name and exp.liveInd=true")
	public List<Customer> findByName(@Param(value = "name") String name);

	// @Query(value = "select exp from Customer exp where exp.category=:category" )
	public List<Customer> findByCategoryAndUserIdAndLiveInd(String category, String userId, boolean liveInd);

	// public Customer findByCustomerId(String customerId);

	@Query(value = "select exp  from Customer exp  where exp.customerId=:customerId and exp.liveInd=true")
	public Customer getcustomerDetailsById(@Param(value = "customerId") String customerId);

	@Query(value = "select a  from Customer a  where a.name=:name and a.liveInd=true")
	public List<Customer> getCustomerListByName(@Param(value = "name") String name);

	public List<Customer> findByNameContainingAndLiveInd(String name, boolean liveInd);

	@Query(value = "select a  from Customer a  where a.sector=:sector and a.liveInd=true")
	public List<Customer> getSectorLinkBySector(@Param(value = "sector") String sector);

	@Query(value = "select a  from Customer a  where a.country=:country and a.liveInd=true")
	public List<Customer> getCountryLinkByCountry(@Param(value = "country") String country);

	public List<Customer> findCustomerByOrganizationId(String organizationId);

	@Query(value = "select a  from Customer a  where a.userId=:userId and a.liveInd=true")
	public List<Customer> getCustomerListByOwnerId(@Param(value = "userId") String userId);

	public List<Customer> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
			boolean b);

	@Query(value = "select exp  from Customer exp  where exp.customerId=:customerId and exp.liveInd=true")
	public List<Customer> getCustomerByCustomerIdAndLiveInd(@Param(value = "customerId") String customerId);

	public List<Customer> findByUserIdAndSourceAndLiveInd(String userId, String sourceId, boolean b);

	@Query(value = "select exp  from Customer exp  where exp.userId=:userId and exp.liveInd=true")
	public List<Customer> getByOrderByNameAsc(@Param(value = "userId") String userId, Pageable pageable);

	@Query(value = "select exp  from Customer exp  where exp.userId=:userId and exp.liveInd=true")
	public List<Customer> getByOrderByNameDesc(@Param(value = "userId") String userId, Pageable pageable);

	@Query("select a from Customer a where a.organizationId = :orgId  and a.liveInd = true")
	public Page<Customer> getCustomerListByOrgIdAndPagging(String orgId, Pageable paging);

	@Query("SELECT a FROM Customer a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.liveInd = true ")
	public List<Customer> getTeamCustomerListByUserIds(@Param(value = "userIds") List<String> userIds);

	@Query("select a from Customer a where a.url = :url  and a.liveInd = true")
	public List<Customer> getByUrl(@Param(value = "url") String url);

	@Query(value = "select a from Customer a where a.userId=:userId and a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	public List<Customer> getCustomerListByUserIdWithDateRange(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Customer a  where a.organizationId=:orgId and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	public List<Customer> getCustomerListByOrgIdWithDateRange(@Param(value = "orgId") String orgId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

	@Query(value = "select a  from Customer a  where (a.userId=:userId OR a.assignedTo=:userId) and  a.creationDate BETWEEN :startDate AND :endDate and a.liveInd=true")
	public List<Customer> getByUserIdAndCreationDateBetween(@Param(value = "userId") String userId,
			@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
	// @Query(value = "select a from Customer a where a.userId=:userId and
	// a.liveInd=true" )
	// public List<Customer> getCustomerByUserId(@Param(value="userId")String
	// userId);

	public Customer findByEmail(String unencryptedemail);

	public List<Customer> findByLiveInd(boolean b);

	@Query(value = "select c  from Customer c  where c.name=:name and c.liveInd=true")
	Customer getByName(@Param("name") String name);

	public List<Customer> findBySectorAndLiveIndAndOrganizationId(String sectorId, boolean b, String orgId);

	public List<Customer> findBySourceAndLiveIndAndOrganizationId(String sourceId, boolean b, String orgId);

	@Query("SELECT a FROM Customer a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.sector=:sectorId AND a.liveInd = true ")
	public List<Customer> getTeamCustomerListByUserIdsAndSector(@Param(value = "userIds") List<String> userIdss,
			@Param(value = "sectorId") String sectorId);

	@Query("SELECT a FROM Customer a WHERE (a.userId IN :userIds OR a.assignedTo IN :userIds) AND a.source=:sourceId AND a.liveInd = true ")
	public List<Customer> getTeamCustomerListByUserIdsAndSource(@Param(value = "userIds") List<String> userIdss,
			@Param(value = "sourceId") String sourceId);

	@Query(value = "select exp  from Customer exp  where (exp.userId = :userId OR exp.assignedTo = :userId) and source=:sourceId and exp.liveInd=true")
	public List<Customer> findByUserIdWithAssignedToAndSourceAndLiveInd(@Param(value = "userId") String userId, @Param("sourceId")String sourceId);

	public List<Customer> findBySectorAndLiveIndAndUserId(String sectorId, boolean b ,String userId);

	public List<Customer> findBySourceAndLiveIndAndUserId(String sourceId, boolean b, String userId);
}
