package com.app.employeePortal.task.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.task.entity.TaskIncludedLink;
@Repository
public interface TaskIncludedLinkRepository  extends JpaRepository<TaskIncludedLink, String>{

	List<TaskIncludedLink> findByTaskId(String taskId);

	@Query(value = "select a  from TaskIncludedLink a  where a.employeeId=:userId and a.liveInd =true" )
	List<TaskIncludedLink> getTaskIncludedLinkByUserId(@Param(value="userId")String userId);

	@Query(value = "select a  from TaskIncludedLink a  where a.employeeId=:userId and a.liveInd =true" )
	Page<TaskIncludedLink> getTaskIncludedLinkByUserIdWithPagination(@Param(value="userId")String userId, Pageable paging);

	List<TaskIncludedLink> findByEmployeeIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate,
			boolean b);
	
}
