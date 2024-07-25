package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Theme;
@Repository
public interface ThemeRepository extends JpaRepository<Theme, String>{

	

}
