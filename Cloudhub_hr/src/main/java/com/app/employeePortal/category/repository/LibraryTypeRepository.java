package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.LibraryType;

@Repository
public interface LibraryTypeRepository extends JpaRepository<LibraryType, String>{

	List<LibraryType> findByOrgId(String orgId);

    List<LibraryType> findByLibraryTypeContainingAndOrgId(String name,String orgId);

	List<LibraryType> findByLibraryTypeAndOrgId(String libraryType, String orgId);


}
