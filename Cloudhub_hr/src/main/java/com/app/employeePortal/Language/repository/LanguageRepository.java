package com.app.employeePortal.Language.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Language.Entity.Languages;
@Repository
public interface LanguageRepository extends JpaRepository<Languages, Long> {

	List<Languages> findByMandatoryInd(boolean b);

	Languages findByBaseInd(boolean b);

}
