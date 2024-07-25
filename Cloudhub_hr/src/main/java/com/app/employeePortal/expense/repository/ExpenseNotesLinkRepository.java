package com.app.employeePortal.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.expense.entity.ExpenseNotesLink;
@Repository
public interface ExpenseNotesLinkRepository  extends JpaRepository<ExpenseNotesLink, String>{

	@Query(value = "select a  from ExpenseNotesLink a  where a.expenseId=:expenseId and a.liveInd = true" )
	public	List<ExpenseNotesLink> getNotesIdByExpenseId(@Param(value="expenseId") String expenseId);

	public ExpenseNotesLink findByNoteId(String notesId);

}
