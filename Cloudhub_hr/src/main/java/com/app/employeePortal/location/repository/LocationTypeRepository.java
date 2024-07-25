//package com.app.employeePortal.location.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.app.employeePortal.location.entity.LocationType;
//@Repository
//public interface LocationTypeRepository extends JpaRepository<LocationType, String> {
//
//	@Query(value = "select exp  from LocationType exp  where exp.locationtypeId=:locationtypeId")
//	LocationType getByLocationtypeId(@Param(value = "locationtypeId")String locationtypeId);
//
//}
