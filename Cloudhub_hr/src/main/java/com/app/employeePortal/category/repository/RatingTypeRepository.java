package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.RatingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingTypeRepository extends JpaRepository<RatingType,String> {
    List<RatingType> findByOrgIdAndLiveInd(String orgId, boolean b);
}
