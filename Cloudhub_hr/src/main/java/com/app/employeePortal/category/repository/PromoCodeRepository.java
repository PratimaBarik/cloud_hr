package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.PromoCodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCodeDetails, String> {

    List<PromoCodeDetails> findByLiveInd(boolean b);
}
